package dev.brunog.dairyapp.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import dev.brunog.dairyapp.model.Diary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    selectedDiary: Diary?,
    onBackPressed: () -> Unit,
    onDeleteClicked: () -> Unit,
    pagerState: PagerState
) {
    Scaffold(
        topBar = {
            WriteTopBar(
                onBackPressed = onBackPressed,
                selectedDiary = selectedDiary,
                onDeleteClicked = onDeleteClicked
            )
        }
    ) { paddingValues ->
        WriteContent(
            paddingValues = paddingValues,
            pagerState = pagerState,
            title = "",
            onTitleChanged = { },
            description = "",
            onDescriptionChanged = {}
        )
    }
}