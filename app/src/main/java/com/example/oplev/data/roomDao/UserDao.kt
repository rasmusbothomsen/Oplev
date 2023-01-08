package com.example.oplev.data.roomDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.oplev.Model.UserInfo

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun addUserInfo(userInfo: UserInfo)

      @Query("select*from user_info")
      fun getAll():List<UserInfo>
}