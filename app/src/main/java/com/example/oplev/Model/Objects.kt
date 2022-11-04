package com.example.oplev.Model

import java.util.Date

data class Idea(
    var title: String,
    var description: String,
    var link: String?,
    var image: String?)

data class Folder(
    var idea: Idea,
    var folder: Folder?,
    var title: String)

data class Journey(
    var tag: String,
    var image: String?,
    var date: Date?,
    var description: String,
    var title: String,
    var folder: Folder?
)