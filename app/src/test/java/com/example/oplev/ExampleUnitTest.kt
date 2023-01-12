package com.example.oplev

import android.util.Log
import com.example.oplev.Model.Folder
import org.junit.Test

import org.junit.Assert.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val testing = Folder("123","123","2323","hello")
        val clasName = testing::class.simpleName
        val attributes = HashMap<String, Any>()
        for (property in testing::class.memberProperties) {
            val attributeName = property.name
            val attributeValue = property.call(testing)
            attributes[attributeName] = attributeValue as Any
        }
        for (at in attributes){
            println(at.key)
            println(at.value)
        }
        println(attributes["id"].toString())

    }

}