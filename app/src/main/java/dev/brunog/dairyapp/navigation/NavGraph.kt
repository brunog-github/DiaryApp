package dev.brunog.dairyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.brunog.dairyapp.navigation.routes.authenticationRoute
import dev.brunog.dairyapp.navigation.routes.homeRoute
import dev.brunog.dairyapp.navigation.routes.writeRoute

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded
        )
        homeRoute(
            onNavigateToWriteScreen = {
                navController.navigate(Screen.Write.route)
            },
            onNavigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded
        )
        writeRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }
}


