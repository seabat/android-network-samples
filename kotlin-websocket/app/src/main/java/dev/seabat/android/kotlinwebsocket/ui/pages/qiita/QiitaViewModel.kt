package dev.seabat.android.kotlinwebsocket.ui.pages.qiita

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.kotlinwebsocket.ErrorStringConverter
import dev.seabat.android.kotlinwebsocket.domain.entity.QiitaArticleListEntity
import dev.seabat.android.kotlinwebsocket.domain.exception.HelloException
import dev.seabat.android.kotlinwebsocket.domain.usecase.FetchQiitaArticlesUseCaseContract
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QiitaViewModel @Inject constructor(
    private val fetchQiitaArticlesUseCase: FetchQiitaArticlesUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _articles =
        MutableLiveData<QiitaArticleListEntity>(QiitaArticleListEntity(arrayListOf()))
    val articles: LiveData<QiitaArticleListEntity>
        get() = _articles

    private var _progressVisible = MutableLiveData<Boolean>(false)
    val progressVisible: LiveData<Boolean>
        get() = _progressVisible

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    /** 検索投稿日 */
    private val _searchDate = MutableLiveData<Date>(Date())
    val searchDate: LiveData<Date>
        get() = _searchDate

    /**
     * @param searchDate: 検索投稿日
     */
    fun loadQiitaArticles(searchDate: Date) {
        _searchDate.postValue(searchDate)
        val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(searchDate)

        viewModelScope.launch {
            _progressVisible.value = true
            kotlin.runCatching {
                fetchQiitaArticlesUseCase(dateString)
            }.onSuccess {
                _articles.value = it
            }.onFailure {
                //NOTE: コルーチンがキャンセルされたとき(JobCancellationException)はエラーを表示しない
                (it as? HelloException)?.let { e ->
                    val errorString = ErrorStringConverter.convertTo(e.errType)
                    android.util.Log.d("Hello", errorString)
                    _errorMessage.value = errorString
                }
            }.also {
                _progressVisible.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}