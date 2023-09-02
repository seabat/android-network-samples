package dev.seabat.android.kotlinwebsocket.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.kotlinwebsocket.domain.repository.GithubRepositoryContract
import dev.seabat.android.kotlinwebsocket.domain.repository.QiitaArticlesRepositoryContract
import dev.seabat.android.kotlinwebsocket.domain.usecase.FetchQiitaArticlesUseCase
import dev.seabat.android.kotlinwebsocket.domain.usecase.FetchQiitaArticlesUseCaseContract
import dev.seabat.android.kotlinwebsocket.domain.usecase.GithubUseCase
import dev.seabat.android.kotlinwebsocket.domain.usecase.GithubUseCaseContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubUseCaseContract(
        githubUseCase: GithubUseCase
    ): GithubUseCaseContract

    @Binds
    @Singleton
    abstract fun bindFetchQiitaArticlesUseCaseContract(
        fetchQiitaArticlesUseCase: FetchQiitaArticlesUseCase
    ): FetchQiitaArticlesUseCaseContract
}


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModuleProvider {
    @Provides
    fun provideGithubUseCase(
        githubRepository: GithubRepositoryContract
    ): GithubUseCase {
        return GithubUseCase(githubRepository)
    }

    @Provides
    fun provideFetchQiitaArticlesUseCase(
        qiitaArticlesRepository: QiitaArticlesRepositoryContract
    ): FetchQiitaArticlesUseCase {
        return FetchQiitaArticlesUseCase(qiitaArticlesRepository)
    }
}
