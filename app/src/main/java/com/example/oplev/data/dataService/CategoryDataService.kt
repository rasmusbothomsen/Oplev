package com.example.oplev.data.dataService

import android.app.Activity
import android.util.Log
import com.example.oplev.MainActivity
import com.example.oplev.Model.Category
import com.example.oplev.Model.Idea
import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.roomDao.CategoryDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*

class CategoryDataService(
    val categoryDao:CategoryDao
) {
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

   suspend fun createCategory(title: String, activity: Activity) {
        val add = HashMap<String,Any>()
       val id = UUID.randomUUID().toString()

        add["createdBy"] = Firebase.auth.currentUser?.uid.toString()
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

        addCategoryLocally(id, title)

    }

    fun addCategoryLocally(id : String, title: String){
        val categoryId = id
        var category = Category(categoryId, title)
        categoryDao.addCategory(category)
        Log.w(AuthViewModel.TAG,"CategoryAdded")
    }

    fun getAllCategories(): List<Category>{

        var categories = categoryDao.getAll()

        return categories
    }
    fun getCategoryDto():List<CategoryDto>{
        var categories = categoryDao.getAll()
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

    fun getCategoryId(Title: String): Int{
        var CategoryDao = MainActivity.database.CategoryDao()
        var categoryId = CategoryDao.getCategoryId(Title)

        return categoryId
    }
}