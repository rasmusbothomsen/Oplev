package com.example.oplev.Model

import java.util.Date

data class Idea(
    val title: String,
    val description: String,
    val link: String?,
    val image: String?)

data class Folder(
    val idea: Idea,
    val folder: Folder?,
    val title: String)

data class Journey(
    val tag: String,
    val image: String?,
    val date: Date?,
    val description: String,
    val title: String,
    val folder: Folder?
)