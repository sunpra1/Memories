package com.sunpra.memories.data.json

import com.google.gson.annotations.SerializedName

data class Memory(
    val id: Int,
    val title: String,
    val description: String,
    val lat: String,
    val lng: String,
    val image: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
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
