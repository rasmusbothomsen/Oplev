package com.example.oplev.data.dataService

import androidx.room.Entity
import com.example.oplev.MainActivity
import com.example.oplev.Model.Category
import com.example.oplev.Model.Idea
import com.example.oplev.data.roomDao.CategoryDao

class CategoryDataService(
    val cateGoryDao:CategoryDao
) {
    fun getAllCategories(): List<Category>{
        var CategoryDao = MainActivity.database.CategoryDao()
        var categories = CategoryDao.getAll()
        var test = Idea(123,123,2,"","","","")


        return categories
    }
}