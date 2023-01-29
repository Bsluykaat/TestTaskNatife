package com.kerumitbsl.core.components.rest

import com.google.gson.JsonElement
import com.kerumitbsl.core.bean.response.GetGifsResponse
import com.kerumitbsl.core.extensions.GIPHY_INIT_KEY
import okhttp3.MultipartBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface TestTaskApi {

    @GET("gifs/trending")
    fun getTrendedGifs(
        @Query("api_key") api_key: String = GIPHY_INIT_KEY,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("random_id") random_id: String,
        @Query("bundle") bundle: String
    ): GetGifsResponse

    @GET("gifs/search")
    fun getSearchedGifs(
        @Query("api_key") api_key: String = GIPHY_INIT_KEY,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") lang: String,
        @Query("random_id") random_id: String,
        @Query("bundle") bundle: String
    ): GetGifsResponse
}