package com.example.oplev.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel<T>(application: Application) :AndroidViewModel(application)  {


    fun readItems(): List<T> {
        val ahahah = mutableListOf<T>()
        return  ahahah
    }

    fun create(item:T){


    }

}