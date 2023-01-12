package com.example.oplev.data.dataService

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.oplev.Model.UserInfo
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.roomDao.UserDao
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class UserDataService(
    val db : FirebaseFirestore,
    val userDao: UserDao
) {
    val add = HashMap<String, Any>()

    suspend fun createAccount(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        baseContext: Context,
        activity: Activity
    ): Boolean {
        var success = false
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(AuthViewModel.TAG, "createUserWithEmail:success")
                    success = true

                    add["firstname"] = firstname
                    add["lastname"] = lastname
                    add["hasOnboarded"] = false
                    add["phoneNum"] = "Intet nummer gemt."

                    db.collection("users")
                        .document(Firebase.auth.currentUser?.uid.toString())
                        .set(add)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                Log.d(AuthViewModel.TAG, "createUserInFirestore:success")
                                success = true
                            } else {
                                Log.w(
                                    AuthViewModel.TAG,
                                    "createUserInFirestore:failure",
                                    task.exception
                                )
                            }
                        }
                } else {
                    Log.w(AuthViewModel.TAG, "createUserWithEmail:failure", task.exception)
                }
            }.await()

        addUserLocally(firstname, lastname, email)

        return success
    }

    fun addUserLocally(firstname: String, lastname: String, email: String) {
        val userInfoId = Firebase.auth.currentUser?.uid.toString()
        var userInfo = UserInfo(userInfoId, email, firstname, lastname,false, "Intet nummer gemt.")

        val existUser = userDao.getUserFromId(userInfoId)
        if (existUser == null){
        userDao.addUserInfo(userInfo)
        Log.d("Useradded", userInfo.eMail)
        }else{
            Log.d("UserAddWarning","User already exist")
        }
    }

    fun sendEmailVerification(activity: Activity) {
        val user = Firebase.auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(activity) { task ->
            }
    }

    /** IF THE EMAIL VERFICATION NEEDS TO BE SENT AGAIN **/
    private fun reload() {

    }

    suspend fun updateOnboarding(activity: Activity) {
            val user = Firebase.auth.currentUser

            db.collection("users")
                .document(user?.uid.toString())
                .update("hasOnboarded", true).addOnCompleteListener(activity) {
                        task ->
                    if (task.isSuccessful){
                        Log.d(TAG, "User email address updated.")
                    }
                }

        updateOBLocally(user?.uid.toString())
        }


    fun updateOBLocally(id : String) {
        val user = userDao.getUserFromId(id)
        runBlocking {
            userDao.updateOBStatus(true, id)
        }
        Log.d("User phone num updated", user.eMail)
    }

    suspend fun deleteUser() {
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(AuthViewModel.TAG, "User account deleted.")
                    deleteUserLocally(user.uid)
                }
            }.await()
    }

    fun deleteUserLocally(id : String) {
        val user = userDao.getUserFromId(id)
        userDao.delete(user)
        Log.d("User deleted", user.eMail)
    }

    suspend fun updateEmail(newEmail : String) {
        val user = Firebase.auth.currentUser

        user!!.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                    updateEmailLocally(user.uid, newEmail)
                }
            }.await()
    }

    fun updateEmailLocally(id : String, newEmail: String) {
        val user = userDao.getUserFromId(id)

        runBlocking {
            userDao.updateEmail(newEmail,id)
        }
        Log.d("User email updated", user.eMail)
    }

    fun getPhoneNum(): String {
        val user = Firebase.auth.currentUser

        return userDao.getPhoneNum(user?.uid.toString())

    }

    fun getFullName(): String {
        val user = Firebase.auth.currentUser
        val userInfo = userDao.getUserFromId(user?.uid.toString())

        return userInfo.firstname + " " + userInfo.lastname

    }

    fun getFirstname(): String {
        return if(Firebase.auth.currentUser == null){
            "Ingen bruger"
        } else {
            val user = Firebase.auth.currentUser
            val userInfo = userDao.getUserFromId(user?.uid.toString())
            userInfo.firstname
        }
    }

   suspend fun addFirebaseuserInRoom(activity:Activity) {
       val docRef = db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
       val userInfo = docRef.get()
           .addOnCompleteListener(activity) { task ->
               if (task.isSuccessful) {
                   Log.d(AuthViewModel.TAG, "retrieveUserName:success")

               } else {
                   Log.d(AuthViewModel.TAG, "retrieveUserName:failed")
               }
           }.await()
       val currentUser = Firebase.auth.currentUser

           addUserLocally(
               userInfo["firstname"].toString(),
               userInfo["lastname"].toString(),
               currentUser?.email!!
           )
   }

    suspend fun updatePhoneNum(newPhoneNum : String, activity: Activity) {
        val user = Firebase.auth.currentUser

        db.collection("users")
            .document(user?.uid.toString())
            .update("phoneNum", newPhoneNum).addOnCompleteListener(activity) {
                task ->
                if (task.isSuccessful){
                        Log.d(TAG, "User email address updated.")
                }
            }
        updatePhoneNumLocally(user?.uid.toString(), newPhoneNum)
    }

    suspend fun updateName(firstname: String, lastname: String, activity: Activity) {
        val user = Firebase.auth.currentUser

        var success = false

        db.collection("users")
            .document(user?.uid.toString())
            .update("firstname", firstname).addOnCompleteListener(activity) {
                    task ->
                if (task.isSuccessful){
                    Log.d(TAG, "User firstname updated.")
                   success = true
                } else {
                    success = false
                }
            }.await()

        db.collection("users")
            .document(user?.uid.toString())
            .update("lastname", lastname).addOnCompleteListener(activity) {
                    task ->
                if (task.isSuccessful){
                    Log.d(TAG, "User lastname updated.")
                    success = true
                }
                else {
                    success = false
                }
            }.await()

            updateNameLocally(user?.uid.toString(), firstname, lastname)

    }

    fun updatePhoneNumLocally(id : String, newPhoneNum: String) {
        val user = userDao.getUserFromId(id)
        runBlocking {
            userDao.updatePhoneNum(newPhoneNum, id)
        }
        Log.d("User phone num updated", user.eMail)
    }

    fun updateNameLocally(id : String, firstname: String, lastname: String) {
        val user = userDao.getUserFromId(id)
        runBlocking {
            userDao.updateFirstName(firstname,id)
            userDao.updateLastName(lastname,id)
        }
        Log.d("User name updated", user.eMail)
    }


    sealed class SignInResult {
        object Success : SignInResult()
        object WrongCredentials : SignInResult()
        object Failed : SignInResult()
    }

    suspend fun signIn(email: String, password: String, baseContext: Context, activity: Activity) : SignInResult {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            SignInResult.Success
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            SignInResult.WrongCredentials
        } catch (e: Exception) {
            SignInResult.Failed
        }
    }

}