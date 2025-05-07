package de.syntax_institut.jetpack.a04_05_online_shopper.api

import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import retrofit2.*
import retrofit2.http.*

interface FakeStoreApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): Response<List<Product>>

    @GET("products")
    suspend fun getProductsWithLimit(
        @Query("limit") limit: Int
    ): Response<List<Product>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>
}
