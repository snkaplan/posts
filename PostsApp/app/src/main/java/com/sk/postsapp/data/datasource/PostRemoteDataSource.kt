package com.sk.postsapp.data.datasource

import com.sk.postsapp.data.model.PostsDto
import com.sk.postsapp.data.model.PostsDtoItem
import com.sk.postsapp.data.model.UpdatePostBody

interface PostRemoteDataSource {
    suspend fun getPosts(): Result<PostsDto>
    suspend fun deletePost(id: Int): Result<Unit>
    suspend fun updatePost(body: UpdatePostBody): Result<PostsDtoItem>
}