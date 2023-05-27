package com.example.submissioncompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailPlayer : Screen("home/{playerId}") {
        fun createRoute(playerId: Int) = "home/$playerId"
    }
}