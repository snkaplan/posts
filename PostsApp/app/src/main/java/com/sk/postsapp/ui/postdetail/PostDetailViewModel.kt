package com.sk.postsapp.ui.postdetail

import androidx.lifecycle.viewModelScope
import com.sk.postsapp.base.BaseViewModel
import com.sk.postsapp.base.Resource
import com.sk.postsapp.base.UIEvent
import com.sk.postsapp.base.UIState
import com.sk.postsapp.base.createUiEvent
import com.sk.postsapp.common.FriendlyMessage
import com.sk.postsapp.common.getFriendlyMessage
import com.sk.postsapp.domain.model.PostItem
import com.sk.postsapp.domain.usecase.UpdatePostUseCase
import com.sk.postsapp.domain.usecase.UpdatePostUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val updatePostUseCase: UpdatePostUseCase,
) : BaseViewModel<PostDetailUIState>() {
    override fun createInitialState(): PostDetailUIState = PostDetailUIState()

    fun init(postItem: PostItem) {
        if (currentState.postItem == null) {
            updateState { copy(postItem = postItem) }
        }
    }

    fun updatePost() {
        viewModelScope.launch {
            currentState.postItem?.let { safePost ->
                updatePostUseCase(
                    UpdatePostUseCaseParams(
                        safePost.id,
                        safePost.userId,
                        safePost.title,
                        safePost.body
                    )
                ).onEach {
                    when (it) {
                        is Resource.Success -> {
                            updateState {
                                copy(
                                    loading = false,
                                    postItem = it.data,
                                    updatePostSuccessEvent = createUiEvent(Unit) {
                                        updateState { copy(updatePostSuccessEvent = null) }
                                    }
                                )
                            }
                        }

                        is Resource.Error -> {
                            updateState {
                                copy(
                                    loading = false,
                                    friendlyMessage = it.exception?.getFriendlyMessage(),
                                    updatePostFailEvent = createUiEvent(Unit) {
                                        updateState { copy(updatePostFailEvent = null) }
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

    fun onPostTitleChanged(title: String) {
        updateState { copy(postItem = postItem?.copy(title = title)) }
    }

    fun onPostBodyChanged(body: String) {
        updateState { copy(postItem = postItem?.copy(body = body)) }
    }
}

data class PostDetailUIState(
    val loading: Boolean = false,
    val postItem: PostItem? = null,
    val updatePostSuccessEvent: UIEvent<Unit>? = null,
    val updatePostFailEvent: UIEvent<Unit>? = null,
    val friendlyMessage: FriendlyMessage? = null,
) : UIState