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
    var title: String,
    var ideas: MutableList<Idea>? = mutableListOf<Idea>()
    )

data class Category(
    var title: String,
    var journeys : MutableList<Journey>
)

data class Journey(
    var tag: String,
    var image: String?,
    var date: Date?,
    var description: String,
    var title: String,
    var folder: MutableList<Folder>? = mutableListOf<Folder>(),
    var ideas: MutableList<Idea>? = mutableListOf<Idea>()
    )