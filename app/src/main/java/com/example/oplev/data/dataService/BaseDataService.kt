package com.example.oplev.data.dataService

import android.app.Activity
import android.util.Log
import com.example.oplev.Model.Journey
import com.example.oplev.data.roomDao.BaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.typeOf

open class BaseDataService<T> (
     val baseDao: BaseDao<T>,
     val queueDataService: QueueDataService
        ){
    protected val db : FirebaseFirestore = FirebaseFirestore.getInstance()

   open suspend fun insertRoom(item: T){
        baseDao.insert(item)
    }

    open suspend fun updateItem(item:T){
        baseDao.update(item)
    }
    open suspend fun deleteItem(item:T){
        baseDao.delete(item)
    }
    open suspend fun getAll(item:T){
        baseDao
    }
    open fun <T: Any> extractDataClassAttributes(data: T): Pair<String?, HashMap<String, Any>> {
        val className = data::class.simpleName
        val attributes = HashMap<String, Any>()
        for (property in data::class.memberProperties) {
            val attributeName = property.name
            val attributeValue = property.call(data)
            attributes[attributeName] = attributeValue as Any
        }
        return className to attributes
    }
    suspend fun gerUserIdFromMail(mail: String): String {
        var id = ""
        var query = db.collection("users")
            .whereEqualTo("email", mail)
            .get()
            .addOnSuccessListener {

            }
            .await()
        if(!query.documents.isEmpty()){
        id = query.documents[0].id
        }

        return id
    }

    open suspend fun checkAndInsertIfShared(item :T){
        val map = extractDataClassAttributes(item as Any)
        val journeyId = map.second.get("journeyId") ?: return
        val sharedJourney = db.collection("sharings").whereEqualTo("journeyId",journeyId).get().await()?:return
        if(sharedJourney.size() == 0){
            return
        }
          val add = map.second
      for(doc in sharedJourney.documents){
          val collabmail = doc.get("collaboratorMail")
          val currentUser = gerUserIdFromMail(collabmail as String)
          if (currentUser == ""){
              break
          }
          db.collection("users").document(currentUser).collection(map.first!!)
              .document(add["id"].toString())
              .set(add)
              .addOnCompleteListener(){
                      task ->
                  if(task.isSuccessful){
                      Log.d("FirebaseInsert",  "STATUS: SUCCESS")
                  }else{

                  }
              }.await()
      }

    }

    open suspend fun insertIntoFireBase(item :T){
        val deconStructedItem = extractDataClassAttributes(item as Any)
        val collectionName = deconStructedItem.first
        val add = deconStructedItem.second
        var sucess = false

        checkAndInsertIfShared(item)
        db.collection("users").document(Firebase.auth.currentUser?.uid.toString()).collection(collectionName!!)
            .document(add["id"].toString())
            .set(add)
            .addOnCompleteListener(){
                    task ->
                if(task.isSuccessful){
                    Log.d("FirebaseInsert",  "STATUS: SUCCESS$collectionName")
                    sucess = true
                }else{

                }
            }
        if (!sucess){
            insertQueueItem(item,add["id"].toString())
        }

    }
    open suspend fun insertItem(item: T){
        insertIntoFireBase(item)
        insertRoom(item)

    }

    open suspend fun insertQueueItem(item:T, itemId:String){
        queueDataService.insertItemToQue(item!!::class.simpleName!!,itemId)
    }

}