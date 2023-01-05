package com.example.oplev.data.dto

import com.example.oplev.Model.*


 class JourneyDto(
     val baseObject:Journey?
 ) {
    var folders = mutableListOf<Folder>()

 }