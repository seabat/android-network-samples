package dev.seabat.android.kotlinwebsocket.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.kotlinwebsocket.data.datasource.github.GithubApiService
import dev.seabat.android.kotlinwebsocket.data.datasource.qiita.QiitaApiService
import dev.seabat.android.kotlinwebsocket.data.repository.GithubRepository
import dev.seabat.android.kotlinwebsocket.data.repository.QiitaArticlesRepository
import dev.seabat.android.kotlinwebsocket.domain.repository.GithubRepositoryContract
import dev.seabat.android.kotlinwebsocket.domain.repository.QiitaArticlesRepositoryContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubRepositoryContract(
        gitHubRepository: GithubRepository
    ): GithubRepositoryContract

    @Binds
    @Singleton
    abstract fun provideQiitaArticlesRepositoryContract(
        qiitaArticlesRepository: QiitaArticlesRepository
    ): QiitaArticlesRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleProvider {
    @Provides
    fun provideGithubRepository(
        endpoint: GithubApiService
    ): GithubRepository {
        return GithubRepository(endpoint)
    }

    @Provides
    fun provideQiitaArticlesRepository(
        endpoint: QiitaApiService
    ): QiitaArticlesRepository {
        return QiitaArticlesRepository(endpoint)
    }
}

