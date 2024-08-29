package com.sk.postsapp.data.datasource

import com.sk.postsapp.data.api.PostService
import com.sk.postsapp.data.model.PostsDto
import com.sk.postsapp.data.model.PostsDtoItem
import com.sk.postsapp.data.model.UpdatePostBody
import com.sk.postsapp.data.utils.handleApi
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(private val postService: PostService) : PostRemoteDataSource {
    override suspend fun getPosts(): Result<PostsDto> {
        return handleApi {
            postService.getPosts()
        }
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return handleApi {
            postService.deletePost(id)
        }
    }

    override suspend fun updatePost(body: UpdatePostBody): Result<PostsDtoItem> {
        return handleApi {
            postService.updatePost(body, body.id)
        }
    }
}