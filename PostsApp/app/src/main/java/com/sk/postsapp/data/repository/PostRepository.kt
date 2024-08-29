package com.sk.postsapp.data.repository

import com.sk.postsapp.data.model.PostsDto
import com.sk.postsapp.data.model.PostsDtoItem

interface PostRepository {
    suspend fun getPosts(): Result<PostsDto>
    suspend fun deletePost(id: Int): Result<Unit>
    suspend fun updatePost(id: Int, userId: Int, title: String, body: String): Result<PostsDtoItem>
}