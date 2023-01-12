package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Category
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.UserDataService
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*


class AuthViewModel(val userDataService: UserDataService, application: Application, val categoryDataService: CategoryDataService):
    BaseViewModel<Category>(
    application
) {
    private val _state = mutableStateOf(States())
    val state: State<States> = _state


    suspend fun deleteUser(){
       userDataService.deleteUser()
    }

    suspend fun getPhoneNum() : String{
        return userDataService.getPhoneNum()
    }

    suspend fun getFullName() : String{
        return userDataService.getFullName()
    }

    suspend fun updateName(fullname: String, activity: Activity){
        var str = fullname
        var delimiter1 = " "

        val parts = str.split(delimiter1)
        var firstname = ""
        var lastname = ""

        if (parts.size > 1){
            firstname = parts[0]
            for (i in 1 until parts.size){
                lastname += parts[i]
            }
        }

        userDataService.updateName(firstname,lastname, activity)
    }


    fun changeDialogVal(){
        val currentValue = state.value.emailDialogState
        _state.value = _state.value.copy(emailDialogState = !currentValue)
    }

    suspend fun updateEmail(email: String){
        userDataService.updateEmail(email)
    }

    suspend fun updatePhoneNum(newPhoneNum: String, activity: Activity){
        userDataService.updatePhoneNum(newPhoneNum, activity)
    }


        suspend fun createNewUser(fullname: String, email: String, password: String, baseContext: Context, activity: Activity){
            var str = fullname
            var delimiter1 = " "

            val parts = str.split(delimiter1)
            var firstname = ""
            var lastname = ""

            if (parts.size > 1){
                firstname = parts[0]
                for (i in 1 until parts.size){
                    lastname += parts[i]
                }
            }

            var success = false

            success = userDataService.createAccount(firstname.toString(),lastname,email,password,baseContext,activity)

            if (Firebase.auth.currentUser != null) {
                categoryDataService.createCategory("Seneste", activity)
                categoryDataService.createCategory("Favoritter", activity)
            }


        val user = Firebase.auth.currentUser
        if (success){
        updateUI(user, true)
        }
        else{
        updateUI(null, false)
        }

    }
     fun signIn(email: String, password: String, baseContext: Context, activity: Activity):UserDataService.SignInResult {
        val result:UserDataService.SignInResult
         runBlocking {
             result =  userDataService.signIn(email, password, baseContext, activity)
         }
         if(result == UserDataService.SignInResult.Success){
             runBlocking {
             userDataService.addFirebaseuserInRoom(activity)
             }
         }

         return  result
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
