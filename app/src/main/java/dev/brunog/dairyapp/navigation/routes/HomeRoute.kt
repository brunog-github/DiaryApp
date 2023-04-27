package dev.brunog.dairyapp.navigation.routes

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunog.dairyapp.data.repository.MongoDB
import dev.brunog.dairyapp.navigation.Screen
import dev.brunog.dairyapp.presentation.components.DisplayAlertDialog
import dev.brunog.dairyapp.presentation.screens.home.HomeScreen
import dev.brunog.dairyapp.presentation.screens.viewmodels.HomeViewModel
import dev.brunog.dairyapp.util.Constants
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun NavGraphBuilder.homeRoute(
    onNavigateToWriteScreen: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val viewModel: HomeViewModel = viewModel()
        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        var signOutDialogOpened by remember { mutableStateOf(false) }

        HomeScreen(
            diaries = diaries,
            drawerState = drawerState,
            onSignOutClicked = { signOutDialogOpened = !signOutDialogOpened },
            onMenuClicked = {
                scope.launch { drawerState.open() }
            },
            onNavigateToWriteScreen = onNavigateToWriteScreen
        )

        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want to Sign Out from your Google Account?",
            dialogOpened = signOutDialogOpened,
            onCloseDialog = { signOutDialogOpened = !signOutDialogOpened },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(Constants.APP_ID).currentUser
                    if (user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            onNavigateToAuth()
                        }
                    }
                }
            }
        )
    }
}