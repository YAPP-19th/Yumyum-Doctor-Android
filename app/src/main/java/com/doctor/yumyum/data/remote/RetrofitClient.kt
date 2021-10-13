package com.doctor.yumyum.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TIMEOUT_COUNT : Long = 10

    fun getClient(): Retrofit {
        val baseUrl = ""

        val baseInterceptor: Interceptor = Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .build()
            chain.proceed(builder)
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_COUNT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_COUNT, TimeUnit.SECONDS)
            .addInterceptor(baseInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}

