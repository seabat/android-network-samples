package dev.seabat.android.kotlinwebsocket.domain.usecase

import dev.seabat.android.kotlinwebsocket.domain.entity.RepositoryListEntity
import dev.seabat.android.kotlinwebsocket.domain.repository.GithubRepositoryContract

class GithubUseCase(val githubRepository: GithubRepositoryContract) : GithubUseCaseContract {
    override suspend fun loadRepos(query: String?): RepositoryListEntity? {
        return githubRepository.fetchRepos(query)
    }
}