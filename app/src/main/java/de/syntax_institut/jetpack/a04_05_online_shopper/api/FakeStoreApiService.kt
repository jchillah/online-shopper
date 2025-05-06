package de.syntax_institut.jetpack.a04_05_online_shopper.api

import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import retrofit2.http.*

interface FakeStoreApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @GET("products")
    suspend fun getProductsWithLimit(@Query("limit") limit: Int): List<Product>
}

