package com.sk.postsapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sk.postsapp.R
import com.sk.postsapp.databinding.ActivityMainBinding
import com.sk.postsapp.ui.components.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val loadingDialogViewModel: LoadingDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_main
        ) as NavHostFragment
        navController = navHostFragment.navController
        listeners()
    }

    private fun listeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    loadingDialogViewModel.viewState.collect { loadingViewState ->
                        if (loadingViewState.isVisible) {
                            LoadingDialog.show(this@MainActivity)
                        } else {
                            LoadingDialog.hide()
                        }
                    }
                }
            }
        }
    }
}