package com.sk.postsapp.ui.postdetail

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sk.postsapp.R
import com.sk.postsapp.base.A101PlusBaseFragment
import com.sk.postsapp.databinding.FragmentPostDetailBinding
import com.sk.postsapp.ui.LoadingDialogViewModel
import com.sk.postsapp.ui.posts.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : A101PlusBaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {
    private val viewModel by viewModels<PostDetailViewModel>()
    private val postsViewModel by activityViewModels<PostsViewModel>()
    private val loadingViewModel: LoadingDialogViewModel by activityViewModels()
    private val args: PostDetailFragmentArgs by navArgs()
    override fun bindScreen() {
        super.bindScreen()
        viewModel.init(args.postItem)
        dataBinding?.apply {
            etPostDetailTitle.addTextChangedListener { text ->
                viewModel.onPostTitleChanged(text.toString())
            }
            etPostDetailBody.addTextChangedListener { text ->
                viewModel.onPostBodyChanged(text.toString())
            }
            btnPostDetailUpdate.setOnClickListener {
                viewModel.updatePost()
            }
            hvPostDetail.onLeftIconClicked { findNavController().navigateUp() }
            ivPostDetailImage.clipToOutline = true
        }
        listeners()
    }

    private fun listeners() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    state.friendlyMessage?.let {
                        handleError(it)
                    }
                    state.postItem?.let { safePostItem ->
                        dataBinding?.apply {
                            hvPostDetail.centerTitleText = safePostItem.title
                            imageUrl = safePostItem.image
                            title = state.title
                            body = state.body
                            buttonEnabled = safePostItem.title != state.title || safePostItem.body != state.body
                        }
                        if (state.loading) {
                            loadingViewModel.show()
                        } else {
                            loadingViewModel.hide()
                        }
                    }
                    state.updatePostFailEvent?.let { safeUpdatePostFailEvent ->
                        safeUpdatePostFailEvent.onConsumed()
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.post_update_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    state.updatePostSuccessEvent?.let { safeUpdatePostSuccessEvent ->
                        safeUpdatePostSuccessEvent.onConsumed()
                        state.postItem?.let {
                            postsViewModel.onPostUpdated(it)
                        }
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.post_updated),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}