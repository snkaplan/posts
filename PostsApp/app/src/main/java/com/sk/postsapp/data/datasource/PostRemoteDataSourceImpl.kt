package com.sk.postsapp.data.datasource

import com.sk.postsapp.data.api.PostService
import com.sk.postsapp.data.model.PostsDto
import com.sk.postsapp.data.utils.handleApi
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(private val postService: PostService) : PostRemoteDataSource {
    override suspend fun getPosts(): Result<PostsDto> {
        return handleApi {
            postService.getPosts()
        }
    }
}