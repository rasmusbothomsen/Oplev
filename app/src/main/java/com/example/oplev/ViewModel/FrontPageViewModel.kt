package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.Model.States
import com.example.oplev.data.dataService.CategoryDataService
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.FrontpageDto
import com.example.oplev.data.roomDao.CategoryDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FrontPageViewModel(application: Application, val categoryDataService: CategoryDataService):BaseViewModel<Category>(
    application
) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _state = mutableStateOf(States())
    val state: State<States> = _state


    fun getCategories(): List<CategoryDto> {
        return categoryDataService.getCategoryDto()

        /**TODO THIS IS TEMP**/
        /*
        var cate = Category(1,"")
        val journey1 = Journey("1","img_denmark","img_norway",1,title ="Danmark", description = "Danmark", date = null)
        val journey2 = Journey("1","img_norway","img_norway",1,title="Norge",description="Norge", date = null)
        val journey3 = Journey("1","img_finland","img_norway",1,title="Finland",description="Finland", date = null)
        val journey4 = Journey("1","img_turkey","img_norway",1,title="Tyrkiet",description="Tyrkiet", date = null)

        var dto = CategoryDto(cate)
        dto.journeys = mutableListOf(journey1,journey2,journey3,journey4)
        return mutableListOf(dto,dto,dto)

         */

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