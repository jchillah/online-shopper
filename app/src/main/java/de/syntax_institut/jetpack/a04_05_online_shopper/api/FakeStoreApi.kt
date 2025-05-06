package de.syntax_institut.jetpack.a04_05_online_shopper.api

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.converter.gson.*
import retrofit2.converter.moshi.*

object FakeStoreAPI {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        // Logging Levels: BODY, BASIC, NONE, HEADERS
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private const val BASE_URL = "https://fakestoreapi.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val retrofitService: FakeStoreApiService by lazy {
        retrofit.create(FakeStoreApiService::class.java)
    }
}