package com.podium.technicalchallenge.common

import androidx.fragment.app.Fragment
import androidx.navigation.NavController


abstract class MovieEvent<T : Fragment> {

    private var hasExecuted: Boolean = false

    fun execute(fragment: T, navController: NavController) {
        if (hasExecuted) {
            return
        }

        hasExecuted = true
        doExecute(fragment, navController)
    }

    protected abstract fun doExecute(fragment: T, navController: NavController)
}