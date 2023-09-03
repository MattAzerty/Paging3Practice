package com.withings.mycomposeandblepractice

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.withings.mycomposeandblepractice.Destinations.SEARCH_ROUTE
import com.withings.mycomposeandblepractice.Destinations.SHOW_ROUTE
import com.withings.mycomposeandblepractice.ui.presentation.screens.search.SearchImageRoute
import com.withings.mycomposeandblepractice.ui.presentation.screens.showCase.ShowCaseRoute
import com.withings.mycomposeandblepractice.utils.printLog

object Destinations {
    const val SEARCH_ROUTE = "search"
    const val SHOW_ROUTE = "show"
}

@Composable
fun MyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = SEARCH_ROUTE,
)
{
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(SEARCH_ROUTE) {
            SearchImageRoute(
                onNextClicked = {
                    navController.navigate(SHOW_ROUTE)
                    }
            )
        }
        composable(SHOW_ROUTE) {
            ShowCaseRoute(
                onBackClicked = {
                    navController.navigate(SEARCH_ROUTE){
                        popUpTo("show") {
                            // To remove back stack
                            inclusive = true
                        }
                    } },
            )
        }
    }
}