package dev.seabat.android.kotlinwebsocket.domain.repository

import dev.seabat.android.kotlinwebsocket.domain.entity.RepositoryListEntity

interface GithubRepositoryContract {
    suspend fun fetchRepos(query: String?): RepositoryListEntity?
}