package dev.seabat.android.kotlinwebsocket.data.datasource.github.model

/**
 * API https://api.github.com/search/repositories のレスポンス
 *
 * 以下のデータを受信する
 * {
 *   "total_count": 1948,
 *   "incomplete_results": false,
 *   "items": [...]
 * }
 */
data class GetAllRepoResponse(val items: List<Repository>)