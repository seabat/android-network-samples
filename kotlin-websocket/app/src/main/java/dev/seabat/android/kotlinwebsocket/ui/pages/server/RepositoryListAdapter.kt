package dev.seabat.android.kotlinwebsocket.ui.pages.server

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.ListitemGithubRepoBinding
import dev.seabat.android.kotlinwebsocket.domain.entity.RepositoryEntity
import dev.seabat.android.kotlinwebsocket.domain.entity.RepositoryListEntity

class RepositoryListAdapter(
    private val onListItemClick: (fullName: String, htmlUrl: String) -> Unit
) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryHolder>() {
    var items = RepositoryListEntity(arrayListOf())

    fun updateRepositoryList(repositoryList: RepositoryListEntity) {
        this.items = repositoryList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListitemGithubRepoBinding.inflate(layoutInflater)
        return RepositoryHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bind(items[position])
        holder.setClickListener(items[position], onListItemClick)
    }

    class RepositoryHolder(val binding: ListitemGithubRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RepositoryEntity) {
            binding.textName.text = data.name
            binding.textDesc.text = data.description ?: ""
            binding.textCreatedDate.text = data.createdAt

            Glide.with(binding.imageThubm)
                .load(data.owner.avatarUrl)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_foreground)
                .error(R.mipmap.ic_launcher_foreground)
                .fallback(R.mipmap.ic_launcher_foreground)
                .into(binding.imageThubm)
        }

        fun setClickListener(
            data: RepositoryEntity,
            onListItemClick: (fullName: String, htmlUrl: String) -> Unit
        ) {
            binding.layoutRoot.setOnClickListener {
                onListItemClick(data.fullName, data.htmlUrl)
            }
        }
    }
}