package com.example.oplev.Model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseUser

@Entity(tableName = "ideas" )
data class Idea(
    @PrimaryKey val id:String = "",
    val journeyId: String= "",
    var folderId:String= "",
    var title: String= "",
    var description: String= "",
    var link: String= "",
    var image: String?= "",
    var date: String= "",
)

@Entity
data class Folder(
    @PrimaryKey val id:String= "",
    var journeyId:String= "",
    var parentFolderId:String= "",
    var title: String= "",
    )

@Entity
data class Category(
    @PrimaryKey val id: String= "",
    var title: String= "",
    var createdBy: String= ""
)

@Entity
data class Journey(
    @PrimaryKey val id:String= "",
    var tag: String= "",
    val categoryID: String= "",
    var date: String?= "",
    var description: String= "",
    var title: String= "",
    var imageId:String? =""

    )

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey val userId:String,
    val eMail:String,
    val firstname: String,
    val lastname: String,
    var hasOnboarded:Boolean = false,
    var phoneNum: String?,
    var imageId:String?,
)
@Entity
data class ImageInfo(
    @PrimaryKey val imageId:String,
    val image:ByteArray
)
@Entity(tableName = "queue_table")
data class QueueItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String= "",
    val objectId: String= "",
    val firstInsert: Boolean= false
)


data class States(
    var signInSuccessful : Boolean = false,
    var user : FirebaseUser? = null,
    var fabExpanded : Boolean = false,
    var dialogState : Boolean = false,
    var nameEditable : Boolean = false,
    var phoneNumEditable : Boolean = false,
    var forgotpassworddialog : Boolean = false,
    var chosenJourneyState: Journey? = null,
    var emailDialogState : Boolean = false,
    var frontPageUpdated: Boolean = false,
    var isLoadingBlur: Dp = 0.dp,
    var emailconf : Boolean = false,
    var deleteuserconf : Boolean = false,
    var logOutdialog: Boolean = false
)


