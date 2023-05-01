package dev.brunog.dairyapp.presentation.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.brunog.dairyapp.data.repository.MongoDB
import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.model.Mood
import dev.brunog.dairyapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId

class WriteViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        getDiaryIdArgument()
        fetchSelectedDiary()
    }

    private fun getDiaryIdArgument() {
        uiState = uiState.copy(
            selectedDiaryId = savedStateHandle.get(key = "diaryId")
        )
    }

    private fun fetchSelectedDiary() {
        if (uiState.selectedDiaryId != null) {
            viewModelScope.launch(Dispatchers.Main) {
                MongoDB.getSelectedDiary(diaryId = ObjectId.invoke(uiState.selectedDiaryId!!))
                    .collect { diary ->
                        if (diary is RequestState.Success) {
                            setTitle(diary.data.title)
                            setDescription(diary.data.description)
                            setMood(diary.data.mood)
                            setSelectedDiary(diary.data)
                        }
                    }
            }
        }
    }

    private fun setSelectedDiary(diary: Diary) {
        uiState = uiState.copy(selectedDiary = diary)
    }

    fun setTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun setDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    private fun setMood(mood: String) {
        uiState = uiState.copy(mood = Mood.valueOf(mood))
    }

    fun insertDiary(diary: Diary, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = MongoDB.insertDiary(diary = diary)
            if (result is RequestState.Success) {
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } else if (result is RequestState.Error) {
                withContext(Dispatchers.Main) {
                    onError(result.error.message.toString())
                }
            }
        }
    }
}

data class UiState(
    val selectedDiaryId: String? = null,
    val selectedDiary: Diary? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral
)