package com.sk.postsapp.di

import com.sk.postsapp.data.datasource.PostRemoteDataSource
import com.sk.postsapp.data.datasource.PostRemoteDataSourceImpl
import com.sk.postsapp.data.repository.PostRepository
import com.sk.postsapp.data.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PostModule {

    @Binds
    fun bindPostRemoteDataSource(sourceImpl: PostRemoteDataSourceImpl): PostRemoteDataSource

    @Binds
    fun bindPostRepository(repositoryImpl: PostRepositoryImpl): PostRepository
}
