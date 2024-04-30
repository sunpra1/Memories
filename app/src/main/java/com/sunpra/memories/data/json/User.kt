package com.sunpra.memories.data.json

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["id"], unique = true)]
)
data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    var token: String?
)