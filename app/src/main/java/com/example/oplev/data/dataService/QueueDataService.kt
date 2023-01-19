package com.example.oplev.data.dataService

import android.util.Log
import com.example.oplev.Model.*
import com.example.oplev.data.AppDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
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
    suspend fun syncDatabases(){

            upDateFirebaseFromQueue()
            getAllFirebaseObjects()

    }
    fun isInternetWorking(): Boolean {
        var success = false
        try {
            val url = URL("https://google.com")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 3000
            connection.connect()
            success = connection.responseCode == 200
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return success
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
   suspend fun upDateFirebaseFromQueue() {
        val items = dao.getAll()
        for (item in items){
            when(item.type)
            {
                "Idea" -> processIdea(item.objectId)
                "Folder" -> processFolder(item.objectId)
                "Category" -> processCategory(item.objectId)
                "Journey" -> processJourney(item.objectId)
            }
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

      fun  insertIntoFireBase(deconStructedItem: Pair<String?, HashMap<String, Any>>){

        val collectionName = deconStructedItem.first
        val add = deconStructedItem.second

            db.collection("users")
                .document(Firebase.auth.currentUser?.uid.toString()).collection(collectionName!!)
                .document(add["id"].toString()).set(add)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("FirebaseInsert", "STATUS: SUCCESS")
                        runBlocking {
                        checkAndInsertIfShared(deconStructedItem)
                        }
                        itemsSynced++
                    }
                }

    } suspend fun gerUserIdFromMail(mail: String): String {
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
    suspend fun signOut() = coroutineScope{
        launch {
        appDatabase.clearAllTables()
        }
    }

    open suspend fun checkAndInsertIfShared(deconStructedItem: Pair<String?, HashMap<String, Any>>) {

        val map = deconStructedItem
        val journeyId = map.second.get("journeyId") ?: return
        val sharedJourney = db.collection("sharings").whereEqualTo("journeyId",journeyId).get().await()?:return
        if(sharedJourney.size() == 0){
            return
        }
        val add = map.second
        for(doc in sharedJourney.documents){
            val collabmail = doc.get("collaboratorMail")
            val currentUser = gerUserIdFromMail(collabmail as String)
            if(currentUser == Firebase.auth.currentUser?.uid.toString()){
                currentUser == doc.get("ownerId")
            }
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
}