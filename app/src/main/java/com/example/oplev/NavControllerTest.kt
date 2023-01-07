package com.example.oplev

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oplev.Model.Journey
import com.example.oplev.Model.TaskViewModelTest
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking


class NavControllerTest {

}
@Composable
fun NavControllerMock(){
    val context = LocalContext.current
    val taskViewModelTest = TaskViewModelTest(context.applicationContext as Application)
    taskViewModelTest.testingAsync(Journey(2,"Hello","",2,"","",""))

    runBlocking {
        val items = taskViewModelTest.getAll()
        for (item in items){
            Log.d("Items",item.tag)
        }
    }



}