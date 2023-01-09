package com.example.oplev.data.dataService

import androidx.room.Entity
import com.example.oplev.MainActivity
import com.example.oplev.Model.Category
import com.example.oplev.Model.Idea
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.roomDao.CategoryDao

class CategoryDataService(
    val cateGoryDao:CategoryDao
) {
    fun getAllCategories(): List<Category>{

        var categories = cateGoryDao.getAll()
        var test = Idea(123,123,2,"","","","")


        return categories
    }
    fun getCategoryDto():List<CategoryDto>{
        var categories = cateGoryDao.getAll()
        var dtos = mutableListOf<CategoryDto>()
        for (category in categories){
            var tempDto = CategoryDto(category)
            tempDto.journeys.addAll(cateGoryDao.getJourneysRelatedToCategory(category.id))
            dtos.add(tempDto)
        }
        return dtos
    }

    fun getCategoryId(Title: String): Int{
        var CategoryDao = MainActivity.database.CategoryDao()
        var categoryId = CategoryDao.getCategoryId(Title)

        return categoryId
    }
}