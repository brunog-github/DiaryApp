package dev.brunog.dairyapp.presentation.screens.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.presentation.components.DisplayAlertDialog
import dev.brunog.dairyapp.util.toInstant
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTopBar(
    selectedDiary: Diary?,
    onBackPressed: () -> Unit,
    onDeleteClicked: () -> Unit,
    moodName: () -> String
) {
    val currentDate by remember { mutableStateOf(LocalDate.now()) }
    val currentTime by remember { mutableStateOf(LocalTime.now()) }
    val formattedDate = remember {
        DateTimeFormatter.ofPattern("dd MMM yyyy").format(currentDate).uppercase()
    }
    val formattedTime = remember {
        DateTimeFormatter.ofPattern("hh:mm a").format(currentTime).uppercase()
    }

    val selectedDiaryTime = remember(selectedDiary) {
        if (selectedDiary != null) {
            SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                .format(Date.from(selectedDiary.date.toInstant())).uppercase()
        } else {
            "$formattedDate, $formattedTime"
        }
    }

    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to home screen"
                )
            }
        },
        title = {
            Column() {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = moodName(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = selectedDiaryTime,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        textAlign = TextAlign.Center
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (selectedDiary != null) {
                DeleteDiaryAction(
                    selectedDiary = selectedDiary,
                    onDeleteConfirmed = onDeleteClicked
                )
            }
        }
    )
}

@Composable
private fun DeleteDiaryAction(
    selectedDiary: Diary?,
    onDeleteConfirmed: () -> Unit
) {
    var isExpended by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = isExpended,
        onDismissRequest = { isExpended = !isExpended }
    ) {
        DropdownMenuItem(
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete")
                }

            },
            onClick = {
                openDialog = !openDialog
                isExpended = !isExpended
            }
        )
    }
    DisplayAlertDialog(
        title = "Delete",
        message = "Are you sure you want to permanently delete this diary note '${selectedDiary?.title}'?",
        dialogOpened = openDialog,
        onCloseDialog = { openDialog = !openDialog },
        onYesClicked = onDeleteConfirmed
    )
    IconButton(onClick = { isExpended = !isExpended }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Overflow menu icon",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}