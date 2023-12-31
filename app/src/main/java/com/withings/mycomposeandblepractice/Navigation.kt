package com.withings.mycomposeandblepractice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.withings.mycomposeandblepractice.Destinations.PARALLAX_ROUTE
import com.withings.mycomposeandblepractice.Destinations.SEARCH_ROUTE
import com.withings.mycomposeandblepractice.Destinations.SHOW_ROUTE
import com.withings.mycomposeandblepractice.ui.presentation.screens.parallax.ParallaxRoute
import com.withings.mycomposeandblepractice.ui.presentation.screens.search.SearchImageRoute
import com.withings.mycomposeandblepractice.ui.presentation.screens.showCase.ShowCaseRoute
import kotlinx.coroutines.flow.collectLatest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun MyNavHost(
    navController: NavHostController = rememberNavController(),
    myNavigator: MyNavigator,
    startDestination: String = SEARCH_ROUTE,
)
{

    LaunchedEffect(Unit) {
        myNavigator.actions.collectLatest { action ->
            when (action) {
                MyNavigator.Action.Back -> navController.popBackStack()
                is MyNavigator.Action.Navigate -> navController.navigate(
                    route = action.destination,
                    builder = action.navOptions
                )
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(SEARCH_ROUTE) {
            SearchImageRoute()
        }
        composable(SHOW_ROUTE) {
            ShowCaseRoute()
        }
        composable(PARALLAX_ROUTE) {
            val encodedPath = it.arguments?.getString("picturePath")
            val picturePath = URLDecoder.decode(encodedPath, StandardCharsets.UTF_8.toString())
            ParallaxRoute(
                picturePath = picturePath!!,
            )
        }
    }
}