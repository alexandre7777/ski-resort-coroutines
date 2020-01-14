package com.alexandre.skiresort.service

import com.alexandre.skiresort.BuildConfig
import com.alexandre.skiresort.service.model.SkiResort
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SkiResortListService{

    /**
     * Get ski resort list.
     */
    @GET("v0/b/ski-resort-be7dc.appspot.com/o/resort-weather.json?alt=media&token=f40092bf-2e06-4077-a84e-0906b834d487")
    suspend fun getSkiResorts(): List<SkiResort>

    companion object {
        private const val BASE_URL = "https://firebasestorage.googleapis.com/"

        fun create(): SkiResortListService {
            val logger = HttpLoggingInterceptor()
            logger.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SkiResortListService::class.java)
        }
    }
}