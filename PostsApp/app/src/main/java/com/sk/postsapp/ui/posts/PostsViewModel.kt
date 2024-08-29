package com.sk.postsapp.ui.posts

import androidx.lifecycle.viewModelScope
import com.sk.postsapp.base.BaseViewModel
import com.sk.postsapp.base.UIState
import com.sk.postsapp.common.FriendlyMessage
import com.sk.postsapp.base.Resource
import com.sk.postsapp.common.getFriendlyMessage
import com.sk.postsapp.domain.model.PostsResult
import com.sk.postsapp.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
) : BaseViewModel<PostsUIState>() {
    override fun createInitialState(): PostsUIState = PostsUIState()

    fun getPosts() {
        viewModelScope.launch {
            if (currentState.postResult == null) {
                getPostsUseCase(Unit).onEach {
                    when (it) {
                        is Resource.Success -> {
                            delay(2000)
                            updateState { copy(loading = false, postResult = it.data) }
                        }

                        is Resource.Error -> {
                            updateState { copy(loading = false, friendlyMessage = it.exception?.getFriendlyMessage()) }
                        }

                        is Resource.Loading -> {
                            updateState { copy(loading = true) }
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    fun deletePostByPosition(position: Int) {
        val newList = currentState.postResult?.posts?.toMutableList()
        newList?.let { safeNewList ->
            safeNewList.removeAt(position)
            updateState { copy(postResult = postResult?.copy(posts = safeNewList.toList())) }
        }
    }
}

data class PostsUIState(
    val loading: Boolean = false,
    val friendlyMessage: FriendlyMessage? = null,
    val postResult: PostsResult? = null,
) : UIState