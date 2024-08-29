package com.sk.postsapp.ui.posts

import androidx.lifecycle.viewModelScope
import com.sk.postsapp.base.BaseViewModel
import com.sk.postsapp.base.UIState
import com.sk.postsapp.common.FriendlyMessage
import com.sk.postsapp.base.Resource
import com.sk.postsapp.base.UIEvent
import com.sk.postsapp.base.createUiEvent
import com.sk.postsapp.common.getFriendlyMessage
import com.sk.postsapp.domain.model.PostsResult
import com.sk.postsapp.domain.usecase.DeletePostUseCase
import com.sk.postsapp.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : BaseViewModel<PostsUIState>() {
    override fun createInitialState(): PostsUIState = PostsUIState()

    fun getPosts() {
        viewModelScope.launch {
            if (currentState.postResult == null) {
                getPostsUseCase(Unit).onEach {
                    when (it) {
                        is Resource.Success -> {
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
        viewModelScope.launch {
            val postId = currentState.postResult?.posts?.get(position)?.id
            postId?.let { safePostId ->
                deletePostUseCase(safePostId).onEach {
                    when (it) {
                        is Resource.Success -> {
                            val newList = currentState.postResult?.posts?.toMutableList()
                            newList?.removeAt(position)
                            updateState {
                                copy(
                                    loading = false,
                                    postResult = postResult?.copy(posts = newList?.toList().orEmpty())
                                )
                            }
                        }

                        is Resource.Error -> {
                            updateState {
                                copy(
                                    loading = false,
                                    friendlyMessage = it.exception?.getFriendlyMessage(),
                                    deletePostFailedEvent = createUiEvent(position) {
                                        updateState { copy(deletePostFailedEvent = null) }
                                    }
                                )
                            }
                        }

                        is Resource.Loading -> {
                            updateState { copy(loading = true) }
                        }
                    }
                }.launchIn(this)
            }
        }
    }
}

data class PostsUIState(
    val loading: Boolean = false,
    val friendlyMessage: FriendlyMessage? = null,
    val postResult: PostsResult? = null,
    val deletePostFailedEvent: UIEvent<Int>? = null,
) : UIState
