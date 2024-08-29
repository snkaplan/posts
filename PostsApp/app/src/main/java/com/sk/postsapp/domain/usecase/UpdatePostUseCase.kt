package com.sk.postsapp.domain.usecase

import com.sk.postsapp.base.UseCase
import com.sk.postsapp.base.Resource
import com.sk.postsapp.base.asResource
import com.sk.postsapp.data.repository.PostRepository
import com.sk.postsapp.domain.model.PostItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val postRepository: PostRepository,
) : UseCase<UpdatePostUseCaseParams, Flow<Resource<PostItem>>> {
    override suspend operator fun invoke(params: UpdatePostUseCaseParams): Flow<Resource<PostItem>> = flow {
        val result = postRepository.updatePost(params.id, params.userId, params.title, params.body)
        emit(result.getOrThrow().serialize())
    }.asResource()
}

data class UpdatePostUseCaseParams(val id: Int, val userId: Int, val title: String, val body: String)