package com.sk.postsapp.domain.usecase

import com.sk.postsapp.base.UseCase
import com.sk.postsapp.base.Resource
import com.sk.postsapp.base.asResource
import com.sk.postsapp.data.repository.PostRepository
import com.sk.postsapp.domain.model.PostsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
) : UseCase<Unit, Flow<Resource<PostsResult>>> {
    override suspend operator fun invoke(param: Unit): Flow<Resource<PostsResult>> = flow {
        val result = postRepository.getPosts()
        emit(result.getOrThrow().serialize())
    }.asResource()
}
