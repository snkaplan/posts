package com.sk.postsapp.ui.splash

import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sk.postsapp.R
import com.sk.postsapp.base.BaseFragment
import com.sk.postsapp.databinding.FragmentSplashBinding
import com.sk.postsapp.ui.utils.navigateSafe
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    private val viewModel by viewModels<SplashViewModel>()
    override fun bindScreen() {
        super.bindScreen()
        dataBinding?.ivSplashLogo?.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.pulse))
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