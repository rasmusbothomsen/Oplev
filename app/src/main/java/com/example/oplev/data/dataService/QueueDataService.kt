package com.example.oplev.data.dataService

import android.util.Log
import com.example.oplev.Model.*
import com.example.oplev.data.AppDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

class QueueDataService(

    val appDatabase:AppDatabase
) {
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    val dao = appDatabase.QueDao()
    private var itemsSynced = 0

    suspend fun insertItemToQue(itemType:String,itemID:String){
        val item = QueueItem(0,itemType,itemID,true)
        dao.insert(item)
    }
    suspend fun syncDatabases():Deferred<Unit>{
        return GlobalScope.async(Dispatchers.IO) {
            upDateFirebaseFromQueue()
                getAllFirebaseObjects()
        }
    }
    suspend fun getAllFirebaseObjects(){
        var ideas:List<Idea> = populateDatClass(Idea::class as KClass<Any>) as List<Idea>

        appDatabase.IdeaDao().insertAllAny(ideas)
        var folders = populateDatClass(Folder::class as KClass<Any>) as List<Folder>
        appDatabase.FolderDao().insertAllAny(folders)
        var category = populateDatClass(Category::class as KClass<Any>) as List<Category>
        appDatabase.CategoryDao().insertAllAny(category)
        var journey = populateDatClass(Journey::class as KClass<Any>) as List<Journey>
        appDatabase.JourneyDao().insertAllAny(journey)


    }
     fun upDateFirebaseFromQueue() {
        val items = dao.getAll()
        for (item in items){
            when(item.type)
            {
                "Idea" -> processIdea(item.objectId)
                "Folder" -> processFolder(item.objectId)
                "Category" -> processCategory(item.objectId)
                "Journey" -> processJourney(item.objectId)
            }

            if (itemsSynced == items.size){
                Log.d("Synced complete","QueItems synced")
                appDatabase.IdeaDao().deleteAll()
                appDatabase.JourneyDao().deleteAll()
                appDatabase.FolderDao().deleteAll()
                appDatabase.CategoryDao().deleteAll()
                appDatabase.QueDao().deleteAll()

            }
            else{
                Log.e("SYNCED FAILED","NOT ALL ITEMS WHERE SYNCED. SYNCED: $itemsSynced: Total: ${items.size}")
            }
        }
    }


     fun processIdea(objectId:String){
       val items  = appDatabase.IdeaDao().getfromId(objectId)
        for (item in items){
            insertIntoFireBase(extractDataClassAttributes(item))
        }
    }fun processFolder(objectId:String){
        val items  = appDatabase.FolderDao().getfromId(objectId)
        for (item in items){
            insertIntoFireBase(extractDataClassAttributes(item))
        }
    }fun processCategory(objectId:String){
        val items  = appDatabase.CategoryDao().getfromId(objectId)
        for (item in items){
            insertIntoFireBase(extractDataClassAttributes(item))
        }
    }fun processJourney(objectId:String){
        val items  = appDatabase.JourneyDao().getfromId(objectId)
        for (item in items){
            insertIntoFireBase(extractDataClassAttributes(item))
        }
    }
    fun <T: Any> extractDataClassAttributes(data: T): Pair<String?, HashMap<String, Any>> {
        val className = data::class.simpleName
        val attributes = HashMap<String, Any>()
        for (property in data::class.memberProperties) {
            val attributeName = property.name
            val attributeValue = property.call(data)
            attributes[attributeName] = attributeValue as Any
        }
        return className to attributes
    }


   suspend fun  populateDatClass(clazz:KClass<Any>):List<Any>{
       var returnItems = ArrayList<Any>()

       val dbCollection = db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
           .collection(clazz.simpleName!!).get().addOnSuccessListener {  }.await()
       val items = dbCollection.documents
       for (item in items){
           returnItems.add(item.toObject(clazz.java)!!)
       }
       Log.d("TranslatedItem",returnItems.toString())
       Log.d("dataClassName",clazz.simpleName!!)

       return returnItems
    }

     fun insertIntoFireBase(deconStructedItem: Pair<String?, HashMap<String, Any>>){

        val collectionName = deconStructedItem.first
        val add = deconStructedItem.second

        db.collection(collectionName!!)
            .document(Firebase.auth.currentUser?.uid.toString())
            .set(add)
            .addOnCompleteListener(){
                    task ->
                if(task.isSuccessful){
                    Log.d("FirebaseInsert",  "STATUS: SUCCESS")
                    itemsSynced ++
                }
            }
    }
}