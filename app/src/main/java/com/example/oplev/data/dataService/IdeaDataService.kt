package com.example.oplev.data.dataService

import com.example.oplev.Model.Idea
import com.example.oplev.data.roomDao.BaseDao
import com.example.oplev.data.roomDao.IdeaDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IdeaDataService(
    val dao: IdeaDao
)