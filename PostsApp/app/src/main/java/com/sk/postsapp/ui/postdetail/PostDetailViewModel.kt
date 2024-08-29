package com.sk.postsapp.ui.postdetail

import com.sk.postsapp.base.BaseViewModel
import com.sk.postsapp.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(

) : BaseViewModel<PostDetailUIState>() {
    override fun createInitialState(): PostDetailUIState = PostDetailUIState()
}

data class PostDetailUIState(val loading: Boolean = false) : UIState