package com.sk.postsapp.base

interface UseCase<P, out T : Any> {
    suspend operator fun invoke(params: P): T
}
