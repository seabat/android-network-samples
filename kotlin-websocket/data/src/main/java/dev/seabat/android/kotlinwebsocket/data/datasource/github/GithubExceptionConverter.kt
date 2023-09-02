package dev.seabat.android.kotlinwebsocket.data.datasource.github

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.seabat.android.kotlinwebsocket.data.datasource.github.model.ErrorResponse
import dev.seabat.android.kotlinwebsocket.domain.exception.ErrorType
import dev.seabat.android.kotlinwebsocket.domain.exception.HelloException

/**
 * GitHub API のエラーレスポンスコードを HelloException に変換する
 */
object GithubExceptionConverter {
    fun convertTo(responseStatusCode: Int, errorBody: String?): HelloException {
        val errorType = when (responseStatusCode) {
            400 -> ErrorType.NETWORK_BAD_REQUEST
            401 -> ErrorType.NETWORK_UNAUTHORIZED
            403 -> ErrorType.NETWORK_FORBIDDEN
            404 -> ErrorType.NETWORK_NOT_FOUND
            else -> ErrorType.NETWORK_UNKNOWN_ERROR
        }
        val errorMessage = errorBody?.let {
            val moshi: Moshi = Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
            jsonAdapter.fromJson(it)
        } ?: null

        return HelloException.HttpException(
            errType = errorType,
            responseStatus = responseStatusCode,
            errMessage = errorMessage?.message ?: null
        )
    }
}