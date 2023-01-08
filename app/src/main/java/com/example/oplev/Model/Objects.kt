package com.example.oplev.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseUser

@Entity(tableName = "ideas" , primaryKeys =["id","ownerId"] )
data class Idea(
    val id:Long,
    val ownerId:Long,
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

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey val userId:String,
    val eMail:String,
    val firstname: String,
    val hasOnboarded:Boolean
)

data class States(
    var signInSuccessful : Boolean = false,
    var user : FirebaseUser? = null,
    var fabExpanded : Boolean = false,
    var dialogState : Boolean = false,
    var nameEditable : Boolean = false,
    var phoneNumEditable : Boolean = false,
    var forgotpassworddialog : Boolean = false
)


