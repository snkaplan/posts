package com.sk.postsapp.data.model

import com.sk.postsapp.common.Constants
import com.sk.postsapp.base.Serializable
import com.sk.postsapp.domain.model.PostItem
import com.sk.postsapp.domain.model.PostsResult

class PostsDto : ArrayList<PostsDtoItem>(), Serializable<PostsResult> {
    override fun serialize(): PostsResult {
        return PostsResult(this.map { it.serialize() })
    }
}

data class PostsDtoItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Serializable<PostItem> {
    override fun serialize(): PostItem {
        return PostItem(
            id = id,
            title = title,
            body = body,
            image = buildString {
                append(Constants.IMAGE_BASE_URL)
                append(id)
                append(Constants.IMAGE_TYPE)
            }
        )
    }
}