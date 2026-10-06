package com.sharechat.android.feed

import com.sharechat.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {

    @GET("v1/feed")
    suspend fun getFeed(
        @Query("user_id") userId: String,
        @Query("lang") lang: String?,
        @Query("cursor") cursor: String? = null,
        @Query("limit") limit: Int = 20,
    ): FeedResponse

    companion object {
        fun create(): FeedApi = Retrofit.Builder()
            .baseUrl(BuildConfig.FEED_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FeedApi::class.java)
    }
}
