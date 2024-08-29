package com.sk.postsapp.data.repository

import com.sk.postsapp.data.datasource.PostRemoteDataSource
import com.sk.postsapp.data.model.PostsDto
import com.sk.postsapp.data.model.PostsDtoItem
import com.sk.postsapp.data.model.UpdatePostBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) : PostRepository {
    override suspend fun getPosts(): Result<PostsDto> {
        return postRemoteDataSource.getPosts()
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return postRemoteDataSource.deletePost(id)
    }

    override suspend fun updatePost(id: Int, userId: Int, title: String, body: String): Result<PostsDtoItem> {
        return postRemoteDataSource.updatePost(UpdatePostBody(id, userId, title, body))
    }
}