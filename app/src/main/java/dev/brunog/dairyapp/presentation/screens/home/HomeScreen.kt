package dev.brunog.dairyapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    onNavigateToWriteScreen: () -> Unit
) {
    HomeNavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(onMenuClicked = onMenuClicked)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onNavigateToWriteScreen) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Create New Diary"
                    )
                }
            },
            content = {

            }
        )
    }
}