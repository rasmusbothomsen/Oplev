package com.example.oplev.data.dataService

import com.example.oplev.MainActivity
import com.example.oplev.Model.Category

class CategoryDataService {
    fun getAllCategories(): List<Category>{
        var CategoryDao = MainActivity.database.CategoryDao()
        var categories = CategoryDao.getAll()

        return categories
    }
}