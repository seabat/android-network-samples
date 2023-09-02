package dev.seabat.android.kotlinwebsocket.domain.repository

import dev.seabat.android.kotlinwebsocket.domain.entity.QiitaArticleListEntity

interface QiitaArticlesRepositoryContract {
    suspend fun fetchItems(query: String?): QiitaArticleListEntity?
}