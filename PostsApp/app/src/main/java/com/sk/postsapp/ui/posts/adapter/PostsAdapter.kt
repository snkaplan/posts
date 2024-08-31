package com.sk.postsapp.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sk.postsapp.databinding.ItemPostBinding
import com.sk.postsapp.domain.model.PostItem

class PostsAdapter(private val listener: PostsListener) :
    ListAdapter<PostItem, PostsAdapter.PostItemViewHolder>(PostsDiffUtil) {
    private lateinit var layoutInflater: LayoutInflater

    object PostsDiffUtil : DiffUtil.ItemCallback<PostItem>() {
        override fun areItemsTheSame(
            oldItem: PostItem,
            newItem: PostItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PostItem,
            newItem: PostItem
        ): Boolean {
            return oldItem.body == newItem.body && oldItem.title == newItem.title && oldItem.image == newItem.image
        }
    }

    inner class PostItemViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postItem: PostItem) {
            with(binding) {
                data = postItem
                binding.root.setOnClickListener {
                    listener.onPostClicked(postItem)
                }
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.PostItemViewHolder {
        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        return PostItemViewHolder(
            ItemPostBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostsAdapter.PostItemViewHolder, position: Int) {
        currentList[position]?.let { holder.bind(it) }
    }
}

interface PostsListener {
    fun onPostClicked(data: PostItem)
}
