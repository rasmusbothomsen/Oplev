package com.example.oplev.ViewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.oplev.Model.States
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class AuthViewModel() : ViewModel() {
    private val _state = mutableStateOf(States())
    val state: State<States> = _state

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    val add = HashMap<String,Any>()

    fun createAccount(firstname: String, lastname: String, email: String, password: String, baseContext: Context, activity: Activity) {
        // [START create_user_with_email]

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Authentication successful.",
                        Toast.LENGTH_SHORT).show()
                    val user = Firebase.auth.currentUser
                    updateUI(user, true)

                    add["firstname"] = firstname
                    add["lastname"] = lastname

                    db.collection("users")
                        .document(Firebase.auth.currentUser?.uid.toString())
                        .set(add)
                        .addOnCompleteListener(activity){
                        taskk ->
                        if (taskk.isSuccessful){
                            Log.d(TAG, "createUserInFirestore:success")
                            Toast.makeText(baseContext, "Creation successful.",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Log.w(TAG, "createUserInFirestore:failure", task.exception)
                            Toast.makeText(baseContext, "Creation failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null, false)
                }
            }
            }
        // [END create_user_with_email]

    fun signIn(email: String, password: String, baseContext: Context, activity: Activity) {
        // [START sign_in_with_email]
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = Firebase.auth.currentUser
                    updateUI(user, true)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null, false)
                }
            }
        // [END sign_in_with_email]
    }

    private fun sendEmailVerification(activity: Activity) {
        // [START send_email_verification]
        val user = Firebase.auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(activity) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    fun updateUI(user: FirebaseUser?, isSuccessful : Boolean) {
        _state.value = _state.value.copy(user = user, signInSuccessful = isSuccessful)
    }

    private fun reload() {

    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}
