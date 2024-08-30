package com.sk.postsapp.ui.posts

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.sk.postsapp.R
import com.sk.postsapp.base.BaseFragment
import com.sk.postsapp.databinding.FragmentPostsBinding
import com.sk.postsapp.domain.model.PostItem
import com.sk.postsapp.ui.LoadingDialogViewModel
import com.sk.postsapp.ui.components.SwipeToDelete
import com.sk.postsapp.ui.posts.adapter.PostsAdapter
import com.sk.postsapp.ui.posts.adapter.PostsListener
import com.sk.postsapp.ui.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>(R.layout.fragment_posts), PostsListener {
    private val viewModel by activityViewModels<PostsViewModel>()
    private val loadingViewModel: LoadingDialogViewModel by activityViewModels()
    private val adapter by lazy { PostsAdapter(this) }

    override fun bindScreen() {
        super.bindScreen()
        viewModel.getPosts()
        initViews()
        listeners()
    }

    private fun initViews() {
        dataBinding?.let { safeDataBinding ->
            safeDataBinding.rvPosts.apply {
                adapter = this@PostsFragment.adapter
                if (itemDecorationCount < 1) {
                    val dividerItemDecoration =
                        DividerItemDecoration(safeDataBinding.root.context, DividerItemDecoration.VERTICAL)
                    addItemDecoration(dividerItemDecoration)
                }
                val swipeHandler = SwipeToDelete(safeDataBinding.root.context) { position ->
                    AlertDialog.Builder(safeDataBinding.root.context)
                        .setTitle(context.getString(R.string.are_you_sure))
                        .setMessage(context.getString(R.string.are_you_sure_warning))
                        .setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                            viewModel.deletePostByPosition(position)
                        }
                        .setNegativeButton(context.getString(R.string.no)) { _, _ ->
                            this@PostsFragment.adapter.notifyItemChanged(position)
                        }
                        .create()
                        .show()
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(this)
            }
        }
    }

    private fun listeners() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    dataBinding?.isLoading = state.loading && state.postResult == null
                    state.friendlyMessage?.let {
                        handleError(it)
                    }
                    state.postResult?.let {
                        adapter.submitList(it.posts)
                        if (state.loading) {
                            loadingViewModel.show()
                        } else {
                            loadingViewModel.hide()
                        }
                    }
                    state.deletePostFailedEvent?.let { safeDeleteFailEvent ->
                        this@PostsFragment.adapter.notifyItemChanged(safeDeleteFailEvent.data)
                        safeDeleteFailEvent.onConsumed()
                    }
                }
            }
        }
    }

    override fun onPostClicked(data: PostItem) {
        findNavController().navigateSafe(PostsFragmentDirections.actionNavigationPostsToNavigationPostDetail(data))
    }
}