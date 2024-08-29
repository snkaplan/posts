package com.sk.postsapp.data.datasource

import com.sk.postsapp.data.model.PostsDto

interface PostRemoteDataSource {
    suspend fun getPosts(): Result<PostsDto>
    suspend fun deletePost(id: Int): Result<Unit>
}