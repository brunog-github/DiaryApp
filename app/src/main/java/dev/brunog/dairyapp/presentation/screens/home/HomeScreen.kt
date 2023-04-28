package dev.brunog.dairyapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.presentation.components.EmptyPage
import dev.brunog.dairyapp.util.RequestState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    diaries: RequestState<Map<LocalDate, List<Diary>>>,
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    onNavigateToWriteScreen: () -> Unit
) {
    var paddingValues by remember { mutableStateOf(PaddingValues()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    HomeNavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopBar(scrollBehavior = scrollBehavior,onMenuClicked = onMenuClicked)
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                    ),
                    onClick = onNavigateToWriteScreen
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Create New Diary"
                    )
                }
            },
            content = {
                paddingValues = it

                when(diaries) {
                    is RequestState.Success -> {
                        HomeContent(
                            diaryNotes = diaries.data,
                            paddingValues = it,
                            onClick = {}
                        )
                    }
                    is RequestState.Error -> {
                        EmptyPage(
                            title = "Error",
                            subtitle = "${diaries.error.message}"
                        )
                    }
                    is RequestState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    else -> {}
                }
            }
        )
    }
}