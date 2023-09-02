package dev.seabat.android.kotlinwebsocket.data.datasource.github.model

import com.squareup.moshi.Json

data class Repository(
    val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "html_url") val htmlUrl: String,
    val description: String?,
    @Json(name = "created_at") val createdAt: String,
    val owner: Owner
)
