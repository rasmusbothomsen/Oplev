import com.example.oplev.ViewModel.AuthViewModel
import com.example.oplev.ViewModel.BaseViewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.*
import com.example.oplev.data.dataService.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*


class CreateJourneyViewModel(val journeydataService: JourneyDataService, val categoryDataService:CategoryDataService, val userDataService: UserDataService, application: Application, val folderDataService: FolderDataService,
                             imageDataService: ImageDataService
): BaseViewModel(application, imageDataService) {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var imageState:Bitmap? = null

        fun createNewJourney(
            tag: String,
            Image: String?,
            CategoryID: String,
            Date: String?,
            Description: String,
            Title: String,
            collaboratorMail: MutableList<String>,
            activity: Activity,
        ) {
            var imageId = ""
            if (imageState != null){
             imageId = upLoadImage(imageState!!)
            }
            val tempJourney = Journey(
                UUID.randomUUID().toString(),
                tag,
                CategoryID,
                Date,
                Description,
                Title,
                imageId
            )
            val baseFolderId = UUID.randomUUID().toString()
            val baseFolderOfJourney =
                Folder(baseFolderId, tempJourney.id, baseFolderId, "Basefolder")

            viewModelScope.launch(Dispatchers.IO) {
                journeydataService.insertItem(tempJourney)
                folderDataService.insertItem(baseFolderOfJourney)

                for (i in 0 until collaboratorMail.size){
                    if (collaboratorMail[i].isNotEmpty()){
                        viewModelScope.launch(Dispatchers.IO) {
                            shareJourney(
                                Firebase.auth.currentUser?.uid.toString(),
                                tempJourney.id,
                                collaboratorMail[i],
                                activity,
                                tempJourney
                            )
                        }
                    }
                }
            }
        }

        fun getCategoryIdFromTitle(Title: String): String {
            var categoryID = " "
            runBlocking {
                categoryID = categoryDataService.getCategoryId(Title)
            }
            return categoryID
        }

        fun getCategories(): List<Category> {
            var id = Firebase.auth.currentUser?.uid
            val categories = categoryDataService.getAllCategories(id.toString())
            return categories
        }

        fun shareJourney(
            ownerId: String,
            journeyId: String,
            collaboratorMail: String,
            activity: Activity,
            tempJourney: Journey
        ) {
            viewModelScope.launch(Dispatchers.IO)  {
                userDataService.shareJourney(ownerId, journeyId, collaboratorMail, activity)
                val collaboratorId = userDataService.gerUserIdFromMail(activity, collaboratorMail)
                journeydataService.insertSharedJourneyToFirebase(
                    tempJourney,
                    collaboratorId,
                    categoryDataService,
                    activity
                )
            }
        }

        suspend fun getUserName(activity: Activity, baseContext: Context): String {
            val docRef = db.collection("users").document(Firebase.auth.currentUser?.uid.toString())

            val userInfo = docRef.get()

                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {

                        Log.d(AuthViewModel.TAG, "retrieveUserName:success")
                    } else {
                        Log.d(AuthViewModel.TAG, "retrieveUserName:failed")

                    }
                }.await()

            return userInfo["firstname"].toString()

        }

    }
