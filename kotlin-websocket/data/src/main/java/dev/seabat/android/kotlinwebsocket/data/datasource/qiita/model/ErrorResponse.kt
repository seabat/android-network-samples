package dev.seabat.android.kotlinwebsocket.data.datasource.qiita.model

/**
 * {
 *     "message": "Not found",
 *     "type": "not_found"
 * }
 *
 * ref. https://qiita.com/api/v2/docs#get-apiv2items
 *
 * ただ、500エラーの場合は上記のレスポンスにはならない
 */
data class ErrorResponse(val message: String, val type: String)