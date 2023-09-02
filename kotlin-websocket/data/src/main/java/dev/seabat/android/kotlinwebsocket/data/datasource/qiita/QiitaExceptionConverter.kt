package dev.seabat.android.kotlinwebsocket.data.datasource.qiita

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.seabat.android.kotlinwebsocket.data.datasource.github.model.ErrorResponse
import dev.seabat.android.kotlinwebsocket.domain.exception.ErrorType
import dev.seabat.android.kotlinwebsocket.domain.exception.HelloException

/**
 * Qiita API のエラーレスポンスコードを HelloException に変換する
 */
object QiitaExceptionConverter {
    fun convertTo(
        statusCode: Int,
        errorBody: String?
    ): HelloException {
        val errorType = when (statusCode) {
            400 -> ErrorType.NETWORK_BAD_REQUEST
            401 -> ErrorType.NETWORK_UNAUTHORIZED //FIXME: 401 は Response.isSuccessful にならない可能性が高い
            403 -> ErrorType.NETWORK_FORBIDDEN //FIXME: 401 は Response.isSuccessful にならない可能性が高い
            404 -> ErrorType.NETWORK_NOT_FOUND //FIXME: 404 は Response.isSuccessful にならない可能性が高い
            500 -> ErrorType.NETWORK_INTERNAL_SERVER_ERROR
            else -> ErrorType.NETWORK_UNKNOWN_ERROR
        }
        val errorMessage = when (errorType) {
            ErrorType.NETWORK_UNKNOWN_ERROR -> {
                val errorResponse = errorBody?.let {
                    convertToErrorResponse(it)
                }
                errorResponse?.let {
                    it.message
                }
            }

            ErrorType.NETWORK_INTERNAL_SERVER_ERROR -> errorBody
            else -> {
                null
            }
        }

        return HelloException.HttpException(
            errType = errorType,
            responseStatus = statusCode,
            errMessage = errorMessage
        )
    }

    private fun convertToErrorResponse(errorBody: String): ErrorResponse? {
        val moshi: Moshi = Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
        return jsonAdapter.fromJson(errorBody)
    }
}