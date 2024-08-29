package com.sk.postsapp.ui.components

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.core.content.ContextCompat
import com.sk.postsapp.R
import javax.inject.Singleton

@Singleton
object LoadingDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        if (dialog != null && dialog?.isShowing == true) {
            return
        }
        dialog = Dialog(context, R.style.LoadingDialogStyle)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.setCancelable(false)
        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent))
        dialog?.show()
    }

    fun hide() {
        if (dialog != null && dialog?.isShowing == true) {
            dialog?.dismiss()
            dialog = null
        }
    }
}