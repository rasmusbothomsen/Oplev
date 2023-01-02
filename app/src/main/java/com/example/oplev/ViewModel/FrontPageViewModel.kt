package com.example.oplev.ViewModel

import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Category

class FrontPageViewModel(var categoryData: CategoryDto) {


    fun getCategories(): MutableList<Category>? {
        return categoryData.categorys
    }

    fun setCategories(categories: List<Category>?) {
        /* TODO */
    }
}