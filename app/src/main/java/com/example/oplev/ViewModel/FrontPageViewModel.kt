package com.example.oplev.ViewModel

import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.Model.Category
import com.example.oplev.data.dto.FrontpageDto

class FrontPageViewModel(var frontpageDto: FrontpageDto) {


    fun getCategories(): MutableList<Category>? {
        return frontpageDto.categories
    }

    fun setCategories(categories: List<Category>?) {
        /* TODO */
    }
}