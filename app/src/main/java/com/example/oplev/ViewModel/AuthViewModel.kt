package com.example.oplev.ViewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.UserDataService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class AuthViewModel(
    val dataService:UserDataService
) : ViewModel() {
    private val _state = mutableStateOf(States())
    val state: State<States> = _state

        // [END create_user_with_email]

        fun createNewUser(firstname: String, lastname: String, email: String, password: String, baseContext: Context, activity: Activity){
            var success = false

                 success = dataService.createAccount(firstname,lastname,email,password,baseContext,activity)

        val user = Firebase.auth.currentUser
        if (success){
        updateUI(user, true)

        }
        else{
        updateUI(null, false)
        }

    }
    suspend fun signIn(email: String, password: String, baseContext: Context, activity: Activity) {
        val success = dataService.signIn(email,password,baseContext,activity)
        val user = Firebase.auth.currentUser
        if (success){
            updateUI(user, true)

        }
        else{
            updateUI(null, false)
        }
    }

    suspend fun sendPasswordReset(mail : String) {
        // [START send_password_reset]

        Firebase.auth.sendPasswordResetEmail(mail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }.await()
        // [END send_password_reset]
    }

    fun nameStateChange(){
        var currentState = state.value.nameEditable
        _state.value = _state.value.copy(nameEditable = !currentState )
    }

    fun PhoneStateChange(){
        var currentState = state.value.phoneNumEditable
        _state.value = _state.value.copy(phoneNumEditable = !currentState )
    }

    fun forgotPasswordStateChange(){
        var currentState = state.value.forgotpassworddialog
        _state.value = _state.value.copy(forgotpassworddialog = !currentState )
    }



    fun updateUI(user: FirebaseUser?, isSuccessful : Boolean) {
        _state.value = _state.value.copy(user = user, signInSuccessful = isSuccessful)
    }



    companion object {
        internal const val TAG = "EmailPassword"
    }

}
