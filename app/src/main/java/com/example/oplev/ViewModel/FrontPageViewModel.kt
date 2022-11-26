package com.example.oplev.ViewModel

import com.example.oplev.Data.CategoryData
import com.example.oplev.Model.Category

class FrontPageViewModel(var categoryData: CategoryData) {


    fun getCategories(): MutableList<Category>? {
        return categoryData.categorys
    }

    fun setCategories(categories: List<Category>?) {
        /* TODO */
    }
}