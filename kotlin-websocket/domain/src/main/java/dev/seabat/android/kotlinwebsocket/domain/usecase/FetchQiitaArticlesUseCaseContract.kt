package dev.seabat.android.kotlinwebsocket.domain.usecase

import dev.seabat.android.kotlinwebsocket.domain.entity.QiitaArticleListEntity

interface FetchQiitaArticlesUseCaseContract {
    suspend operator fun invoke(
        startCreatedAt: String,
        title: String? = null
    ): QiitaArticleListEntity?
}