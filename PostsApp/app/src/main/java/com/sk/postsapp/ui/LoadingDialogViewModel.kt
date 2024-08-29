package com.sk.postsapp.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoadingDialogViewModel @Inject constructor() : ViewModel() {
    private var _viewState = MutableStateFlow(LoadingDialogViewState())
    val viewState: StateFlow<LoadingDialogViewState> = _viewState

    fun show() {
        _viewState.value = viewState.value.copy(isVisible = true)
    }

    fun hide() {
        _viewState.value = viewState.value.copy(isVisible = false)
    }
}

data class LoadingDialogViewState(
    val isVisible: Boolean = false
)
