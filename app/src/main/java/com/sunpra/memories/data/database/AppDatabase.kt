package com.sunpra.memories.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunpra.memories.data.database.dao.MemoryDao
import com.sunpra.memories.data.database.dao.UserDao
import com.sunpra.memories.data.json.Memory
import com.sunpra.memories.data.json.User

@Database(
    version = 1,
    entities = [User::class, Memory::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
            }.also { instance = it }
    }

    abstract fun getUserDao(): UserDao

    abstract fun getMemoryDao(): MemoryDao

}