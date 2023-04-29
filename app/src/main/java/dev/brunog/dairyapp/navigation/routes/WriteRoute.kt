package dev.brunog.dairyapp.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dev.brunog.dairyapp.navigation.Screen
import dev.brunog.dairyapp.presentation.screens.write.WriteScreen

@OptIn(ExperimentalPagerApi::class)
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
        val pagerState = rememberPagerState()

        WriteScreen(
            selectedDiary = null,
            pagerState = pagerState,
            onBackPressed = onBackPressed,
            onDeleteClicked = {}
        )
    }
}