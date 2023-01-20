package com.example.oplev.ViewModel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oplev.Model.ImageInfo
import com.example.oplev.data.dataService.ImageDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Float
import java.util.*

open class BaseViewModel(application: Application, val imageDataService: ImageDataService) :AndroidViewModel(application)  {


    fun upLoadImage(bitmap: Bitmap):String{
        val imageByteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,80, imageByteArray)
        val imageInfo = ImageInfo(UUID.randomUUID().toString(),imageByteArray.toByteArray())
        viewModelScope.launch(Dispatchers.IO) {
            imageDataService.insertImage(imageInfo)
        }
        return imageInfo.imageId
    }

     fun getImage(width:Int,height:Int , imageId:String): Bitmap? {
        val imageInfo = imageDataService.getImageFromId(imageId)
        val imageByteArray: ByteArray = imageInfo.image
        val imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        val scaleWidth = width.toFloat() / imageBitmap.width
        val scaleHeight = height.toFloat() / imageBitmap.height
        val scale = Float.min(scaleWidth, scaleHeight)
        val matrix = Matrix()
        matrix.setScale(scale, scale)
        try {
           return Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.width, imageBitmap.height, matrix, true)
        }catch (e:IOException){
            return null
        }
    }

}