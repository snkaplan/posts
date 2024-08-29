package com.sk.postsapp.ui.postdetail

import androidx.fragment.app.viewModels
import com.sk.postsapp.R
import com.sk.postsapp.base.A101PlusBaseFragment
import com.sk.postsapp.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : A101PlusBaseFragment<FragmentPostsBinding>(R.layout.fragment_post_detail) {
    private val viewModel by viewModels<PostDetailViewModel>()
    override fun bindScreen() {
        super.bindScreen()
    }
}