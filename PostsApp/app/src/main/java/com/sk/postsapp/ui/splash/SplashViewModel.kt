package com.sk.postsapp.ui.splash

import androidx.lifecycle.viewModelScope
import com.sk.postsapp.base.BaseViewModel
import com.sk.postsapp.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseViewModel<SplashUIState>() {
    override fun createInitialState(): SplashUIState = SplashUIState()

    init {
        viewModelScope.launch {
            delay(500)
            updateState { copy(navigateToPosts = true) }
        }
    }

    fun onNavigateToPostsConsumed() {
        updateState { copy(navigateToPosts = false) }
    }
}

data class SplashUIState(val navigateToPosts: Boolean = false) : UIState
