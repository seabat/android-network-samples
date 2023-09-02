package dev.seabat.android.kotlinwebsocket.domain.entity

class QiitaArticleListEntity(val qiitas: ArrayList<QiitaArticleEntity>) :
    List<QiitaArticleEntity> by qiitas {
    fun get(title: String): QiitaArticleEntity? {
        return qiitas.firstOrNull() {
            it.title == title
        }
    }
}