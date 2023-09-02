package dev.seabat.android.kotlinwebsocket.domain.exception

enum class ErrorType(val value: Int) {
    CONNECTION_TIMEOUT(101),

    /** 通信タイムアウト */
    SOCKET_TIMEOUT(102),

    /** 接続先にが見つからない */
    UNKNOWN_HOST(103),

    /** 通信 IO エラー */
    NETWORK_IO_ERROR(104),

    /** 400: 無効なリクエストデータ */
    NETWORK_BAD_REQUEST(105),

    /** 401: 認証失敗*/
    NETWORK_UNAUTHORIZED(106),

    /** 403: 認可失敗(認証済みだがアクセス権限がない) */
    NETWORK_FORBIDDEN(107),

    /** 404: アクセスしたリソースが存在しない  */
    NETWORK_NOT_FOUND(108),

    /** 500: Internal Server Error */
    NETWORK_INTERNAL_SERVER_ERROR(109),

    /** 不明なネットワークエラー */
    NETWORK_UNKNOWN_ERROR(199),

    UNKNOWN_ERROR(9999)
}