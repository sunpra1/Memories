package com.sunpra.memories.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunpra.memories.data.json.User
import com.sunpra.memories.data.json.UserWithMemories

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE token = :token LIMIT 1")
    suspend fun getUserHavingToken(token: String): User

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun getUserWithMemoriesHavingId(id: Int): UserWithMemories

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}