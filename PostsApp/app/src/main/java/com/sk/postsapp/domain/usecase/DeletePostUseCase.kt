package com.sk.postsapp.domain.usecase

import com.sk.postsapp.base.UseCase
import com.sk.postsapp.base.Resource
import com.sk.postsapp.base.asResource
import com.sk.postsapp.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository,
) : UseCase<Int, Flow<Resource<Unit>>> {
    override suspend operator fun invoke(param: Int): Flow<Resource<Unit>> = flow {
        val result = postRepository.deletePost(param)
        emit(result.getOrThrow())
    }.asResource()
}
