package com.sk.postsapp.data.api

import com.sk.postsapp.data.model.PostsDto
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("posts")
    suspend fun getPosts(): Response<PostsDto>
}