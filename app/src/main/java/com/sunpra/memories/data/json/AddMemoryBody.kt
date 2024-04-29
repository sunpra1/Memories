package com.sunpra.memories.data.json

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

data class AddMemoryBody(
    val title: String,
    val description: String,
    val image: InputStream,
    val lat: Double = 0.0,
    val lng: Double = 0.0
) {
    fun asMultipartBody(): List<MultipartBody.Part> {
        val titlePart = MultipartBody.Part.createFormData("title", title)
        val descriptionPart = MultipartBody.Part.createFormData("description", description)
        val latPart = MultipartBody.Part.createFormData("lat", lat.toString())
        val lngPart = MultipartBody.Part.createFormData("lng", lng.toString())
        val imageFile = File.createTempFile(UUID.randomUUID().toString(), ".png")
            .apply { FileOutputStream(this).use { image.copyTo(it) } }
        val imagePart = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            imageFile.asRequestBody(contentType = "image/*".toMediaType())
        )
        return listOf(titlePart, descriptionPart, imagePart, latPart, lngPart)
    }
}
