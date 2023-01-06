package com.example.oplev.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.firebase.auth.FirebaseUser
import java.util.Date

@Entity
data class Idea(
    @PrimaryKey var id:Int,
    var folderId:Int,
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
    var image: String?,
    val categoryID: Int,
    var date: String?,
    var description: String,
    var title: String,

    )

data class User(
    val firstname: String
)

data class States(
    var signInSuccessful : Boolean = false,
    var user : FirebaseUser? = null,
    var fabExpanded : Boolean = false
)


