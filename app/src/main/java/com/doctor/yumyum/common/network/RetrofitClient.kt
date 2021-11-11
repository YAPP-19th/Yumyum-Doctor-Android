package com.doctor.yumyum.common.network

import com.doctor.yumyum.data.repository.LoginRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TIMEOUT_COUNT: Long = 10
    val repository: LoginRepositoryImpl = LoginRepositoryImpl()

    fun getClient(): Retrofit {
        val baseUrl = "http://10.0.2.2:8000"

        val baseInterceptor: Interceptor = Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .addHeader("auth", repository.getLoginToken().toString())
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

