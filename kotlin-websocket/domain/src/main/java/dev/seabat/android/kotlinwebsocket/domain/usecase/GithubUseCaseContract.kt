package dev.seabat.android.kotlinwebsocket.domain.usecase

import dev.seabat.android.kotlinwebsocket.domain.entity.RepositoryListEntity

interface GithubUseCaseContract {
    suspend fun loadRepos(query: String?): RepositoryListEntity?
}