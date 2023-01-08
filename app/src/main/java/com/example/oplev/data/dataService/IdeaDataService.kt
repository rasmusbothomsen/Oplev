package com.example.oplev.data.dataService

import com.example.oplev.Model.Idea
import com.example.oplev.data.roomDao.BaseDao
import com.google.firebase.ktx.Firebase

class IdeaDataService(baseDao: BaseDao<Idea>,
                      queueDataService: QueueDataService
) :BaseDataService<Idea>(baseDao, queueDataService) {


}