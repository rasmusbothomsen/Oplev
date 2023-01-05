package com.example.oplev.ViewModel
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.oplev.Model.*
import com.example.oplev.data.*
import com.example.oplev.data.dto.CategoryDto
import com.example.oplev.data.dto.FrontpageDto
import com.example.oplev.data.dto.JourneyDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class CreateJourneyViewModel(var journey: JourneyDto, var categoryData: CategoryDto, var frontpageDto: FrontpageDto) {
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    val add = HashMap<String,Any>()

    fun createJourneyInDB(destination : String, category: String, beskrivelse: String, activity: Activity, baseContext : Context){
        add["createdBy"] = Firebase.auth.currentUser?.uid.toString()
        add["destination"] = destination
        add["category"] = category
        add["description"] = beskrivelse

        db.collection("journeys")
            .document(Firebase.auth.currentUser?.uid.toString())
            .set(add)
            .addOnCompleteListener(activity){
                    task ->
                if (task.isSuccessful){
                    Log.d(AuthViewModel.TAG, "createJourney:success")
                    Toast.makeText(baseContext, "Creation successful.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(AuthViewModel.TAG, "createJourney:failure", task.exception)
                    Toast.makeText(baseContext, "Creation failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun createNewJourney(destination : String, category: String, beskrivelse: String){
        Log.d("beskrivelse",beskrivelse)
        Log.d("destination",destination)
        Log.d("category",category)
        var tempJourney = Journey(tag ="e",image = "img_finland", categoryID = 1, description = beskrivelse, date = null, title = "test")
        categoryData.journeys.add(tempJourney)
    }
    fun getCategories():MutableList<Category> {
        return frontpageDto.categories
    }
}