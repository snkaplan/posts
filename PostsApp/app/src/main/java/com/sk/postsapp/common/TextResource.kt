package com.sk.postsapp.common

import android.content.Context
import androidx.annotation.StringRes

sealed class TextResource {
    data class DynamicString(val value: String) : TextResource()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : TextResource()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}