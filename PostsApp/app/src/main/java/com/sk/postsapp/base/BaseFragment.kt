package com.sk.postsapp.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.sk.postsapp.R
import com.sk.postsapp.common.FriendlyMessage
import com.sk.postsapp.ui.utils.DialogUtils

abstract class BaseFragment<T : ViewDataBinding>(private var layoutId: Int) : Fragment() {

    protected var dataBinding: T? = null
    private var errorDialog: AlertDialog? = null

    open fun bindScreen() {
        dataBinding?.lifecycleOwner = viewLifecycleOwner
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
        return dataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dataBinding == null) return
        bindScreen()
    }

    fun handleError(friendlyMessage: FriendlyMessage) {
        if (errorDialog != null) {
            return
        }
        errorDialog = DialogUtils.getErrorDialog(
            requireActivity(),
            friendlyMessage.title.asString(requireContext()),
            friendlyMessage.description.asString(requireContext()),
            getString(R.string.ok)
        ) {
            errorDialog = null
        }
        errorDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding?.unbind()
        dataBinding = null
    }
}