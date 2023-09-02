package dev.seabat.android.kotlinwebsocket.data.datasource.qiita

import dev.seabat.android.kotlinwebsocket.data.datasource.qiita.model.QiitaArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QiitaApiService {
    @GET("items")
    fun getItems(
        @Header("Authorization") token: String,
        @Query("page") page: String,
        @Query("per_page") per_page: String,
        @Query("query") query: String
    ): Call<Array<QiitaArticle>>
}