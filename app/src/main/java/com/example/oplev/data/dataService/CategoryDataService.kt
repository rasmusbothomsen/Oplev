package com.example.oplev.data.dataService

import android.app.Activity
import android.util.Log
import com.example.oplev.MainActivity
import com.example.oplev.Model.Category
import com.example.oplev.Model.Idea
import com.example.oplev.Model.Journey
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.roomDao.CategoryDao
import com.example.oplev.data.roomDao.JourneyDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*

class CategoryDataService(
    val categoryDao:CategoryDao
) {
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

   suspend fun createCategory(title: String, activity: Activity) {
        val add = HashMap<String,Any>()
       val id = UUID.randomUUID().toString()
       val createdBy = Firebase.auth.currentUser?.uid.toString()

        add["createdBy"] = createdBy
        add["categoryTitle"] = title
       add["id"] = id

        db.collection("category")
            .add(add)
            .addOnCompleteListener(activity){
                    task ->
                if (task.isSuccessful){
                    Log.d(AuthViewModel.TAG, "createUserInFirestore:success")
                } else {
                    Log.w(AuthViewModel.TAG, "createUserInFirestore:failure")
                }
            }.await()

        addCategoryLocally(id, title, createdBy)

    }

    fun addCategoryLocally(id : String, title: String, createdBy: String){
        val categoryId = id
        var category = Category(categoryId, title, createdBy)
        categoryDao.addCategory(category)
        Log.w(AuthViewModel.TAG,"CategoryAdded")
    }

    fun getAllCategories(id: String): List<Category>{

        var categories = categoryDao.getAll(id)

        return categories
    }

    suspend fun getSharedJourneyIds(): List<String> {
        var journeyIds = mutableListOf<String>()
        db.collection("sharings")
            .whereEqualTo("collaboratorMail", Firebase.auth.currentUser?.email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    journeyIds.add(document.data["journeyId"].toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(AuthViewModel.TAG, "Error getting documents: ", exception)
            }.await()
        return journeyIds
    }


    fun getCategoryDto(id: String):List<CategoryDto>{
        var categories = categoryDao.getAll(id)
        var dtos = mutableListOf<CategoryDto>()

        for (i in 0..categories.size-1){
            var tempDto = CategoryDto(categories[i])
            tempDto.journeys.addAll(categoryDao.getJourneysRelatedToCategory(categories[i].id))
            dtos.add(tempDto)
        }
        /*
        for (category in categories){
            var tempDto = CategoryDto(category)
            tempDto.journeys.addAll(categoryDao.getJourneysRelatedToCategory(category.id))
            dtos.add(tempDto)
        }

         */
        return dtos
    }

    fun getCategoryId(Title: String): String{

        return categoryDao.getCategoryId(Title)
    }
}