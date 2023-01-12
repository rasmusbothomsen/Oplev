package com.example.oplev.data.roomDao

import androidx.room.*
import com.example.oplev.Model.UserInfo

@Dao
interface UserDao:BaseDao<UserInfo> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun addUserInfo(userInfo: UserInfo)

      @Query("select*from user_info")
      fun getAll():List<UserInfo>
      @Query("select*from user_info where userId == :id")
      fun getUserFromId(id:String):UserInfo
      @Query("select phoneNum from user_info where userId == :id")
      fun getPhoneNum(id:String):String
  @Query("select hasOnboarded from user_info where userId == :id")
  fun getOBStatus(id:String):Boolean
      @Query("UPDATE user_info SET phoneNum=:newPhoneNum WHERE userId = :id")
      fun updatePhoneNum(newPhoneNum : String, id: String)
    @Query("UPDATE user_info SET hasOnboarded=:obstatus WHERE userId = :id")
    fun updateOBStatus(obstatus : Boolean, id: String)
      @Query("UPDATE user_info SET eMail=:newEmail WHERE userId = :id")
      fun updateEmail(newEmail : String, id: String)
      @Query("UPDATE user_info SET firstname=:newFirstName WHERE userId = :id")
      fun updateFirstName(newFirstName : String, id: String)
      @Query("UPDATE user_info SET lastname=:newLastName WHERE userId = :id")
      fun updateLastName(newLastName : String, id: String)

}
