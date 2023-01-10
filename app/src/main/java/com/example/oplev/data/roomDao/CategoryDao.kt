package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey
import com.example.oplev.Model.UserInfo

@Dao
interface CategoryDao:BaseDao<Category> {
    @Query("Select*from category")
    fun getAll(): List<Category>

    @Query("select * from Journey where categoryID like :id")
    fun getJourneysRelatedToCategory(id:String): List<Journey>

    @Query("select id from category where title like :Title")
    fun getCategoryId(Title: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category: Category)

}