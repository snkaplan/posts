package com.sk.postsapp.data.repository

import com.sk.postsapp.data.datasource.PostRemoteDataSource
import com.sk.postsapp.data.model.PostsDto
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) : PostRepository {
    override suspend fun getPosts(): Result<PostsDto> {
        return postRemoteDataSource.getPosts()
    }
}