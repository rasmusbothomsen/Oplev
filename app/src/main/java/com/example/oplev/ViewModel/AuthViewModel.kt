package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Category
import com.example.oplev.Model.ImageInfo
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dataService.ImageDataService
import com.example.oplev.data.dataService.QueueDataService
import com.example.oplev.data.dataService.UserDataService
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Float.min
import java.util.*


class AuthViewModel(val userDataService: UserDataService, application: Application, val categoryDataService: CategoryDataService, val queueDataService: QueueDataService,
val imageDataService: ImageDataService):
    BaseViewModel<Category>(
    application
) {
    private val _state = mutableStateOf(States())
    val state: State<States> = _state
    val isLoading = mutableStateOf(false)
    val syncdone = mutableStateOf(false)
    var storageRef = Firebase.storage.reference


    suspend fun deleteUser(){
       userDataService.deleteUser()
    }

    suspend fun getPhoneNum() : String{
        return userDataService.getPhoneNum()
    }

    suspend fun getFullName() : String{
        return userDataService.getFullName()
    }

     fun syncDatabases(){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            loadingBlurChange()
        queueDataService.syncDatabases()
            isLoading.value = false
            syncdone.value = true
        }
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

    fun sletbrugerdialog(){
        val currentValue = state.value.deleteuserconf
        _state.value = _state.value.copy(deleteuserconf = !currentValue)
    }


    suspend fun updateEmail(email: String){
        userDataService.updateEmail(email)
    }

    suspend fun updatePhoneNum(newPhoneNum: String, activity: Activity){
        userDataService.updatePhoneNum(newPhoneNum, activity)
    }


        suspend fun createNewUser(fullname: String, email: String, password: String, baseContext: Context, activity: Activity){
            var str = fullname.split(" ", limit = 2)
            var firstname = ""
            var lastname =""
            if(str.size ==2){
                 firstname = str[0]
                 lastname = str[1]
            }else{
            }
            if(firstname.isEmpty()){
                firstname = fullname
            }

            var success = false
            viewModelScope.launch(Dispatchers.IO){
                launch {
                    success = userDataService.createAccount(firstname.toString(),lastname,email,password,baseContext,activity)
                    if (Firebase.auth.currentUser != null) {
                        categoryDataService.createCategory("Seneste", activity)
                        categoryDataService.createCategory("Favoritter", activity)
                        categoryDataService.createCategory("Delt med mig", activity)
                    }
                }.invokeOnCompletion {
                    launch {
                        val user = Firebase.auth.currentUser
                        if (user!=null){
                            updateUI(user, true)
                        }
                        else{
                            updateUI(null, false)
                        }
                    }
                }

            }






    }
     fun signIn(email: String, password: String, baseContext: Context, activity: Activity):UserDataService.SignInResult {
        val result:UserDataService.SignInResult
        isLoading.value = true
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

    fun loadingBlurChange(){
        var currentState = state.value.isLoadingBlur
        if (currentState == 0.dp) {
            _state.value = _state.value.copy(isLoadingBlur = 16.dp )
        } else {
            _state.value = _state.value.copy(isLoadingBlur = 0.dp )
        }
    }



    fun updateUI(user: FirebaseUser?, isSuccessful : Boolean) {
        _state.value = _state.value.copy(user = user, signInSuccessful = isSuccessful)
    }

    fun upLoadImage(bitmap: Bitmap){
        val imageByteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,80, imageByteArray)
        val imageInfo = ImageInfo(UUID.randomUUID().toString(),imageByteArray.toByteArray())
        viewModelScope.launch(Dispatchers.IO) {
        imageDataService.insertImage(imageInfo)
        }



    }

    fun getImage(width:Int,height:Int , imageId:String):Bitmap{
        val imageInfo = imageDataService.getImageFromId(imageId)
        val imageByteArray: ByteArray = imageInfo.image
        val imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        val scaleWidth = width.toFloat() / imageBitmap.width
        val scaleHeight = height.toFloat() / imageBitmap.height
        val scale = min(scaleWidth, scaleHeight)
        val matrix = Matrix()
        matrix.setScale(scale, scale)
        return Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.width, imageBitmap.height, matrix, true)
    }



    companion object {
        internal const val TAG = "EmailPassword"
    }

}
