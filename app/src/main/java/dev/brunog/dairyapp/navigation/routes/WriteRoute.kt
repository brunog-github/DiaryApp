package dev.brunog.dairyapp.navigation.routes

import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dev.brunog.dairyapp.model.Mood
import dev.brunog.dairyapp.navigation.Screen
import dev.brunog.dairyapp.presentation.screens.viewmodels.WriteViewModel
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
        val writeViewModel: WriteViewModel = viewModel()
        val context = LocalContext.current
        val uiState = writeViewModel.uiState
        val pagerState = rememberPagerState()
        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }

        WriteScreen(
            uiState = uiState,
            pagerState = pagerState,
            moodName = { Mood.values()[pageNumber].name },
            onBackPressed = onBackPressed,
            onDeleteClicked = {
                writeViewModel.deleteDiary(
                    onSuccess = {
                        onBackPressed()
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    },
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onDescriptionChanged = { writeViewModel.setDescription(it) },
            onTitleChanged = { writeViewModel.setTitle(it) },
            onSaveClicked = {
                writeViewModel.upsertDiary(
                    diary = it.apply { mood = Mood.values()[pageNumber].name },
                    onSuccess = { onBackPressed() },
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onDateTimeUpdated = { writeViewModel.updateDateTime(it) }
        )
    }
}