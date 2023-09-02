package dev.seabat.android.kotlinwebsocket.ui.pages.client

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.seabat.android.kotlinwebsocket.databinding.ListitemQiitaArticleBinding
import dev.seabat.android.kotlinwebsocket.domain.entity.QiitaArticleEntity
import dev.seabat.android.kotlinwebsocket.domain.entity.QiitaArticleListEntity
import dev.seabat.android.kotlinwebsocket.utils.convertToJapaneseCalender

class QiitaArticleListAdapter(
    private val onListItemClick: (title: String, htmlUrl: String) -> Unit
) : RecyclerView.Adapter<QiitaArticleListAdapter.QiitaArticleHolder>() {
    var items = QiitaArticleListEntity(arrayListOf())

    fun updateArticleList(articleList: QiitaArticleListEntity) {
        this.items = articleList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QiitaArticleHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListitemQiitaArticleBinding.inflate(layoutInflater)
        return QiitaArticleHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QiitaArticleHolder, position: Int) {
        holder.bind(items[position])
        holder.setClickListener(items[position], onListItemClick)
    }

    class QiitaArticleHolder(val binding: ListitemQiitaArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QiitaArticleEntity) {
            binding.textTitle.text = data.title
            binding.textCreatedDate.text = convertToJapaneseCalender(data.createdAt)
        }

        fun setClickListener(
            data: QiitaArticleEntity,
            onListItemClick: (title: String, url: String) -> Unit
        ) {
            binding.layoutRoot.setOnClickListener {
                onListItemClick(data.title, data.url)
            }
        }
    }

}