package dev.seabat.android.kotlinwebsocket.ui.pages.qiitasearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QiitaSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _startDate = MutableLiveData<Date>(Date())
    val startDate: LiveData<Date>
        get() = _startDate

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    /**
     *
     * @param dateString ex. 2023-07-01
     */
    fun setStartDate(dateString: String) {
        _startDate.value = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).parse(dateString)
    }

    /**
     * 検索する
     */
    fun search() {
        //TODO:
    }

    /**
     * エラーをクリアする
     */
    fun clearError() {
        _errorMessage.value = null
    }
}