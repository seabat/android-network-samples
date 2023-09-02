package dev.seabat.android.kotlinwebsocket.data.datasource.qiita

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object QiitaApi {
    private const val baseUrl = "https://qiita.com/api/v2/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val qiitaApiService: QiitaApiService by lazy {
        retrofit.create(QiitaApiService::class.java)
    }
}