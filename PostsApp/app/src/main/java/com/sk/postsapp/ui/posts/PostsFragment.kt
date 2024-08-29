package com.sk.postsapp.ui.posts

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.sk.postsapp.R
import com.sk.postsapp.base.A101PlusBaseFragment
import com.sk.postsapp.databinding.FragmentPostsBinding
import com.sk.postsapp.domain.model.PostItem
import com.sk.postsapp.ui.components.SwipeToDelete
import com.sk.postsapp.ui.posts.adapter.PostsAdapter
import com.sk.postsapp.ui.posts.adapter.PostsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : A101PlusBaseFragment<FragmentPostsBinding>(R.layout.fragment_posts), PostsListener {
    private val viewModel by viewModels<PostsViewModel>()
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
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes") { dialog, id ->
                            viewModel.deletePostByPosition(position)
                        }
                        .setNegativeButton("No") { dialog, id ->
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
                    dataBinding?.isLoading = state.loading
                    state.friendlyMessage?.let {
                        // Show dialog for error
                    }
                    state.postResult?.let {
                        adapter.submitList(it.posts)
                    }
                }
            }
        }
    }

    override fun onPostClicked(data: PostItem) {
        findNavController().navigate(PostsFragmentDirections.actionNavigationPostsToNavigationPostDetail())
    }
}