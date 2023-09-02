package dev.seabat.android.kotlinwebsocket.domain.exception

import java.io.IOException

sealed class HelloException(val errType: ErrorType, errMessage: String? = null) :
    Exception(errMessage) {
    class HttpException(errType: ErrorType, errMessage: String? = null, val responseStatus: Int) :
        HelloException(errType, errMessage)

    class NetworkTimeoutException(errType: ErrorType, errMessage: String? = null) :
        HelloException(errType, errMessage)

    class StorageIOException(errType: ErrorType, errMessage: String? = null) :
        HelloException(errType, errMessage)

    companion object {
        /**
         * Throwable を HelloException に変換する
         */
        fun convertTo(throwable: Throwable): HelloException {
            return when (throwable) {
                is java.net.ConnectException -> {
                    // オフラインの場合
                    // ex. java.net.ConnectException: Failed to connect to api.github.com/20.27.177.116:443
                    NetworkTimeoutException(
                        errType = ErrorType.CONNECTION_TIMEOUT,
                        errMessage = throwable.message
                    )
                }

                is java.net.SocketTimeoutException -> {
                    // TCP コネクションタイムアウト
                    NetworkTimeoutException(
                        errType = ErrorType.SOCKET_TIMEOUT,
                        errMessage = throwable.message
                    )
                }

                is java.net.UnknownHostException -> {
                    NetworkTimeoutException(
                        errType = ErrorType.UNKNOWN_HOST,
                        errMessage = throwable.message
                    )
                }

                is IOException -> {
                    // その他のタイムアウトエラー
                    NetworkTimeoutException(
                        errType = ErrorType.NETWORK_IO_ERROR,
                        errMessage = throwable.message
                    )
                }

                else -> {
                    NetworkTimeoutException(
                        errType = ErrorType.NETWORK_UNKNOWN_ERROR,
                        errMessage = throwable.message
                    )
                }
            }
        }
    }
}