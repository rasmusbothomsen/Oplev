package com.example.oplev.ViewModel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.Idea
import com.example.oplev.Model.UserInfo
import com.example.oplev.data.dataService.IdeaDataService
import com.example.oplev.data.dataService.UserDataService
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboadringViewModel(val dataService: UserDataService, application: Application):BaseViewModel<UserInfo>(
    application) {

    fun onboadringComplete(activity: Activity){
        viewModelScope.launch(Dispatchers.IO) {
            dataService.updateOnboarding(activity)
        }
    }



}