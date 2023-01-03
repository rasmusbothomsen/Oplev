package com.example.oplev.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseUser
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
    )

@Entity
data class Category(
    @PrimaryKey val id: Int,
    var title: String,
    var journeys: MutableList<Journey>
)

@Entity
data class Journey(
    var tag: String,
    /*
    @ForeignKey (entity = Category::class, parentColumns = ["id"],
        childColumns = ["categoryID"],
        onDelete = ForeignKey.SET_NULL)

     */
    var image: String?,
    val categoryID: Int,
    var date: Date?,
    var description: String,
    var title: String,

    )

data class User(
    val firstname: String
)

data class States(
    var signInSuccessful : Boolean = false,
    var user : FirebaseUser? = null
)


