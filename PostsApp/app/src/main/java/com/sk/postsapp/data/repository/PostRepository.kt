package com.sk.postsapp.data.repository

import com.sk.postsapp.data.model.PostsDto

interface PostRepository {
    suspend fun getPosts(): Result<PostsDto>
    suspend fun deletePost(id: Int): Result<Unit>
}