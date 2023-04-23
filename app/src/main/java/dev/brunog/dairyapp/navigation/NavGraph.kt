package dev.brunog.dairyapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import dev.brunog.dairyapp.presentation.screens.auth.AuthenticationScreen
import dev.brunog.dairyapp.presentation.screens.viewmodels.AuthenticationViewModel
import dev.brunog.dairyapp.ui.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val authenticated by viewModel.authenticated
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            loadingState = loadingState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            oneTapState = oneTapState,
            authenticated = authenticated,
            messageBarState = messageBarState,
            onTokenIdReceived = { tokenId ->

                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        viewModel.setLoading(false)
                        messageBarState.addError(it)
                    }
                )
            },
            onDialogDismissed = {
                messageBarState.addError(Exception(it))
                viewModel.setLoading(false)
            },
            navigateToHome = {
                navigateToHome()
            }
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    scope.launch { App.create(APP_ID) .currentUser?.logOut()}
                }
            ) {
                Text(text = "Logout")
            }
        }
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = "diaryId") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}
