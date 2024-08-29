package com.sk.postsapp.common

import com.sk.postsapp.R

class GeneralException(val friendlyMessage: FriendlyMessage) : Exception()

fun Throwable.getFriendlyMessage(
    defaultTitle: TextResource = TextResource.StringResource(R.string.error),
    defaultDescription: TextResource = TextResource.StringResource(R.string.general_error_desc)
): FriendlyMessage {
    return if (this is GeneralException) {
        this.friendlyMessage
    } else {
        FriendlyMessage(
            title = defaultTitle,
            description = defaultDescription
        )
    }
}