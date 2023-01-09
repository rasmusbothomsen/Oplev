package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Query
import com.example.oplev.Model.Category
import com.example.oplev.Model.Journey

@Dao
interface CategoryDao {
    @Query("Select*from category")
    fun getAll(): List<Category>

    @Query("select * from Journey where categoryID like :id")
    fun getJourneysRelatedToCategory(id:Int): List<Journey>

    @Query("select id from category where title like :Title")
    fun getCategoryId(Title: String): Int

}