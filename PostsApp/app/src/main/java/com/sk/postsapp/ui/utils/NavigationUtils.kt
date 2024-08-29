package com.sk.postsapp.ui.utils

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    currentDestination?.let { safeCurrentDestination ->
        val action = safeCurrentDestination.getAction(directions.actionId) ?: graph.getAction(directions.actionId)
        if (action != null) {
            navigate(directions, navOptions)
        } else {
            Log.e(
                "NavigationError",
                "Navigation $directions can not be found from here(${safeCurrentDestination.label})."
            )
        }
    }
}