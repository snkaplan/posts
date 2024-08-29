package com.sk.postsapp.data.api

import com.sk.postsapp.data.model.PostsDto
import com.sk.postsapp.data.model.PostsDtoItem
import com.sk.postsapp.data.model.UpdatePostBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostService {
    @GET("posts")
    suspend fun getPosts(): Response<PostsDto>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>

    @PUT("posts/{id}")
    suspend fun updatePost(@Body updatePostBody: UpdatePostBody, @Path("id") id: Int): Response<PostsDtoItem>
}