package com.example.oplev.data.dataService

import android.app.Activity
import android.util.Log
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.roomDao.CategoryDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*

class CategoryDataService(
    val categoryDao:CategoryDao,queueDataService: QueueDataService
): BaseDataService<Category>(categoryDao, queueDataService ) {

   suspend fun createCategory(title: String, activity: Activity) {
       val id = UUID.randomUUID().toString()
       val createdBy = Firebase.auth.currentUser?.uid.toString()
       val tempCategory = Category(id, title, createdBy)

       insertItem(tempCategory)
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

    suspend fun getCategoryIdFromTitle(title: String, userId: String, activity: Activity) : String{
        var id = ""
        var query = db.collection("users").document(userId).collection("Category")
            .whereEqualTo("title", title)
            .get()
            .addOnSuccessListener {

            }
            .await()

        id = query.documents[0].id

        return id
    }

    fun getCategoryDto(id: String):List<CategoryDto>{
        var categories = categoryDao.getAll(id)
        var dtos = mutableListOf<CategoryDto>()

        for (i in categories.indices){
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

    suspend fun getCategoryId(Title: String): String{
        var id = ""
        var query = db.collection("users").document(Firebase.auth.currentUser?.uid.toString()).collection("Category")
            .whereEqualTo("title", Title)
            .get()
            .addOnSuccessListener {

            }
            .await()

        id = query.documents[0].id

        return id
    }
}