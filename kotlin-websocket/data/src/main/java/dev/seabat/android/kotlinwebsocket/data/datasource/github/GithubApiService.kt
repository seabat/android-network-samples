package dev.seabat.android.kotlinwebsocket.data.datasource.github

import dev.seabat.android.kotlinwebsocket.data.datasource.github.model.GetAllRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories")
    fun getAllRepo(@Query("q") q: String): Call<GetAllRepoResponse>
}

