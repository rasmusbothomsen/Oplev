package com.example.oplev.data.dto

import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey

class CategoryDto(
       val baseObject:Category?
) {

        var journeys = mutableListOf<Journey>()

}