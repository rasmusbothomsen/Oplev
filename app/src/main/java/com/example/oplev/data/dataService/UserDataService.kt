package com.example.oplev.data.dataService

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.oplev.Model.UserInfo
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.roomDao.UserDao
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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
    val add = HashMap<String, Any>()

    fun createAccount(
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
            }

        addUserLocally(firstname, email)

        return success
    }

    fun addUserLocally(firstname: String, email: String) {
        val userInfoId = Firebase.auth.currentUser?.uid.toString()
        var userInfo = UserInfo(userInfoId, email, firstname, false)

        userDao.addUserInfo(userInfo)
        Log.d("Useradded", userInfo.eMail)
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

    suspend fun updateOnboarding() {
        val user = Firebase.auth.currentUser?.uid
        if (user != null) {
            val userinfo = userDao.getUserFromId(user)
            userinfo.hasOnboarded = true
            userDao.update(userinfo)
        }
    }

    suspend fun deleteUser() {
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(AuthViewModel.TAG, "User account deleted.")
                }
            }.await()
    }

    suspend fun updateEmail(newEmail : String) {
        val user = Firebase.auth.currentUser

        user!!.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                }
            }.await()
    }

    suspend fun signIn(email: String, password: String, baseContext: Context, activity: Activity) : Boolean {
        var success = false
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(AuthViewModel.TAG, "signInWithEmail:success")
                    success = true
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    success = false
                }
            }.await()
        return success
    }
}