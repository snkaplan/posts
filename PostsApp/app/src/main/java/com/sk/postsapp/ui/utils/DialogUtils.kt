package com.sk.postsapp.ui.utils

import android.app.Activity
import android.app.AlertDialog

object DialogUtils {
    fun getErrorDialog(
        activity: Activity?,
        title: String?,
        message: String?,
        buttonText: String?,
        buttonClick: (() -> Unit)?
    ): AlertDialog {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        if (title?.isNotEmpty() == true) dialog.setTitle(title)
        if (message?.isNotEmpty() == true) dialog.setMessage(message)
        dialog.setCancelable(false)
        dialog.setPositiveButton(buttonText) { p0, _ ->
            p0?.dismiss()
            buttonClick?.invoke()
        }
        return dialog.show()
    }
}
