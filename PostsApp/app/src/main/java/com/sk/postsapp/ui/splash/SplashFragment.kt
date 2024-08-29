package com.sk.postsapp.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sk.postsapp.R
import com.sk.postsapp.base.A101PlusBaseFragment
import com.sk.postsapp.databinding.FragmentSplashBinding
import com.sk.postsapp.ui.utils.navigateSafe
import kotlinx.coroutines.launch

class SplashFragment : A101PlusBaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    private val viewModel by viewModels<SplashViewModel>()
    override fun bindScreen() {
        super.bindScreen()
        listeners()
    }

    private fun listeners() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    if (state.navigateToPosts) {
                        viewModel.onNavigateToPostsConsumed()
                        findNavController().navigateSafe(
                            SplashFragmentDirections.actionNavigationSplashToNavigationPosts(),
                            null
                        )
                    }
                }
            }
        }
    }
}