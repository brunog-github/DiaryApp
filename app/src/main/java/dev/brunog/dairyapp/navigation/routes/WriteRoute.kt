package dev.brunog.dairyapp.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.brunog.dairyapp.navigation.Screen
import dev.brunog.dairyapp.presentation.screens.write.WriteScreen

fun NavGraphBuilder.writeRoute(
    onBackPressed: () -> Unit
) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = "diaryId") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        WriteScreen(
            selectedDiary = null,
            onBackPressed = onBackPressed,
            onDeleteClicked = {}
        )
    }
}