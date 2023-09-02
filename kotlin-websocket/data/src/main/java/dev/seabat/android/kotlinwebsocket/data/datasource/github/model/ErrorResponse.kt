package dev.seabat.android.kotlinwebsocket.data.datasource.github.model

/**
 * GitHub API はレスポンスコードごとにレスポンスボディのフォーマットが異なるが、
 * message は必ず入っているので message のみパースする
 *
 * ref. https://docs.github.com/ja/rest/overview/resources-in-the-rest-api?apiVersion=2022-11-28
 */
data class ErrorResponse(val message: String)