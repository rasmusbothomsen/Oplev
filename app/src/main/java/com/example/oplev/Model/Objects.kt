package com.example.oplev.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Idea(
    @PrimaryKey var id:Int,
    var title: String,
    var description: String,
    var link: String?,
    var image: String?)

@Entity
data class Folder(
    @PrimaryKey val id:Int,
    var journeyId:Int,
    var parentFolderId:Int,
    var title: String,
    )

@Entity
data class Category(
    @PrimaryKey val id: Int,
    var title: String,
)
@Entity
data class Journey(
    @PrimaryKey var id:Int,
    var tag: String,
    @ForeignKey (entity = Category::class, parentColumns = ["id"],
        childColumns = ["categoryID"],
        onDelete = ForeignKey.SET_NULL)
    var image: String?,
    val categoryID: Int,
    //var date: Date?,
    var description: String,
    var title: String,

    )
