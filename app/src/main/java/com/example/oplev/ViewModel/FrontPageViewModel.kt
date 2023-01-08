package com.example.oplev.ViewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.oplev.Model.Category
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.FrontpageDto
import com.example.oplev.data.roomDao.CategoryDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FrontPageViewModel(var frontpageDto: FrontpageDto) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = mutableStateOf(States())
    val state: State<States> = _state

    fun getCategories(): MutableList<CategoryDto>? {
        return frontpageDto.categories
    }

    fun setCategories(categories: List<Category>?) {
        /* TODO */
    }

    suspend fun getUserName(activity: Activity, baseContext: Context): String {
        val docRef = db.collection("users").document(Firebase.auth.currentUser?.uid.toString())

        val userInfo = docRef.get()

            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    Log.d(AuthViewModel.TAG, "retrieveUserName:success")
                    /*
                    Toast.makeText(
                        baseContext, "Retrieved successfully.",
                        Toast.LENGTH_SHORT
                    ).show()

                     */
                } else {
                    Log.d(AuthViewModel.TAG, "retrieveUserName:failed")
                    /*
                    Toast.makeText(
                        baseContext, "Retrieved failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                     */
                }
            }.await()

        return userInfo["firstname"].toString()

    }

    fun signOut(){
        Firebase.auth.signOut()
    }

    fun expandFab(){
        val currentValue = state.value.fabExpanded
        _state.value = _state.value.copy(fabExpanded = !currentValue)
    }

    fun changeDialogVal(){
        val currentValue = state.value.dialogState
        _state.value = _state.value.copy(dialogState = !currentValue)
    }

}