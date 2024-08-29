package com.sk.postsapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UIState> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _uiEvent: MutableSharedFlow<UIEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    val currentState: State get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState

    protected fun updateState(reduce: State.() -> State) {
        _uiState.update { currentState.reduce() }
    }

    protected fun setEvent(event: UIEvent) {
        viewModelScope.launch { _uiEvent.emit(event) }
    }
}
