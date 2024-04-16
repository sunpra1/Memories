package com.sunpra.memories.utility

import com.sunpra.memories.data.RestService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Provider {

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://sunilprasai.com.np/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val restService: RestService = retrofit.create(RestService::class.java)
}