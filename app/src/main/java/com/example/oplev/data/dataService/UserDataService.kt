package com.example.oplev.data.dataService

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.oplev.Model.UserInfo
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.roomDao.UserDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserDataService(
    val db : FirebaseFirestore,
    val userDao: UserDao
) {
    val add = HashMap<String,Any>()

    fun createAccount(firstname: String, lastname: String, email: String, password: String, baseContext: Context, activity: Activity):Boolean {
        // [START create_user_with_email]
        var success = false
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(AuthViewModel.TAG, "createUserWithEmail:success")
                    success = true
                    /*
                    Toast.makeText(baseContext, "Authentication successful.",
                        Toast.LENGTH_SHORT).show()

                     */
                    add["firstname"] = firstname
                    add["lastname"] = lastname
                    add["hasOnboarded"] = false


                    db.collection("users")
                        .document(Firebase.auth.currentUser?.uid.toString())
                        .set(add)
                        .addOnCompleteListener(activity){
                                taskk ->
                            if (taskk.isSuccessful){
                                Log.d(AuthViewModel.TAG, "createUserInFirestore:success")

                                /*
                                Toast.makeText(baseContext, "Creation successful.",
                                    Toast.LENGTH_SHORT).show()
                                 */
                                success = true
                            } else {
                                Log.w(AuthViewModel.TAG, "createUserInFirestore:failure", task.exception)
                                /*
                                Toast.makeText(baseContext, "Creation failed.",
                                    Toast.LENGTH_SHORT).show()

                                 */
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(AuthViewModel.TAG, "createUserWithEmail:failure", task.exception)
                    /*
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                     */
                }
            }

            addUserLocally(firstname,email)

        return success
    }

     fun addUserLocally(firstname: String,email: String){
        val userInfoId = Firebase.auth.currentUser?.uid.toString()
        var userInfo = UserInfo(userInfoId,email,firstname,false)

            userDao.addUserInfo(userInfo)
            Log.d("Useradded",userInfo.eMail)
    }
    fun sendEmailVerification(activity: Activity) {
        // [START send_email_verification]
        val user = Firebase.auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(activity) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    /** IF THE EMAIL VERFICATION NEEDS TO BE SENT AGAIN **/
    private fun reload() {

    }

    suspend fun signIn(email: String, password: String, baseContext: Context, activity: Activity):Boolean {
        var success = false
        // [START sign_in_with_email]
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(AuthViewModel.TAG, "signInWithEmail:success")
                    val user = Firebase.auth.currentUser
                    success = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(AuthViewModel.TAG, "signInWithEmail:failure", task.exception)
                    /*
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                     */
                }
            }.await()
        // [END sign_in_with_email]
        return  success
    }
}