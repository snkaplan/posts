package com.sk.postsapp.base

interface UIEvent<T> {
    val data: T
    val onConsumed: () -> Unit
}

fun <T> createUiEvent(
    data: T,
    onConsumed: () -> Unit
): UIEvent<T> {
    return object : UIEvent<T> {
        override val data: T = data
        override val onConsumed: () -> Unit = onConsumed
    }
}