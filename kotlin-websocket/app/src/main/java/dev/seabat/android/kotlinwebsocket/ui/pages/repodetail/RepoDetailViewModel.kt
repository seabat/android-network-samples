package dev.seabat.android.kotlinwebsocket.ui.pages.repodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.kotlinwebsocket.domain.usecase.GithubUseCaseContract
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}