package dev.seabat.android.kotlinwebsocket.domain.entity

import java.util.Date

data class QiitaArticleEntity(
    val totalCount: Int?,
    val createdAt: Date,
    val title: String,
    val url: String
)
