package com.sunpra.memories.data.json

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "memory",
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["user_id"], unique = false)
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Memory(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val lat: String,
    val lng: String,
    val image: String,
    @SerializedName("created_at")
    @ColumnInfo("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: String,
    @ColumnInfo("user_id")
    var userId: Int?
)
//{
//        "id": 1,
//        "title": "Visit To Bali",
//        "description": "Had a great fun in bali.",
//        "lat": "26.78585",
//        "lng": "85.25588",
//        "image": "1713756985_bali_trip.jpg",
//        "user_id": "1",
//        "created_at": "2024-04-22T03:36:25.000000Z",
//        "updated_at": "2024-04-22T03:36:25.000000Z"
//    }
