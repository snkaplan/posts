package com.sk.postsapp.data.utils

import com.sk.postsapp.R
import com.sk.postsapp.common.FriendlyMessage
import com.sk.postsapp.common.GeneralException
import com.sk.postsapp.common.TextResource
import retrofit2.Response

fun throwError(description: String? = null): Nothing {
    val descriptionValue =
        if (description != null) {
            TextResource.DynamicString(description)
        } else {
            TextResource.StringResource(R.string.general_error_desc)
        }
    throw GeneralException(
        friendlyMessage = FriendlyMessage(
            title = TextResource.StringResource(R.string.error),
            description = descriptionValue,
        )
    )
}

inline fun <reified T : Any> Response<T>.getBodyOrThrowError(): T {
    if (isSuccessful) {
        val body = body()
        return body ?: throwError()
    } else {
        throwError(errorBody()?.string())
    }
}

suspend inline fun <reified T : Any> handleApi(
    execute: () -> Response<T>,
): Result<T> {
    return runCatching {
        val response = execute()
        response.getBodyOrThrowError()
    }
}
