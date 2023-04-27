package dev.brunog.dairyapp.presentation.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.brunog.dairyapp.data.repository.MongoDB
import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.util.RequestState
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel : ViewModel() {

    var diaries: MutableState<RequestState<Map<LocalDate, List<Diary>>>> =
        mutableStateOf(RequestState.Idle)

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries() {
        viewModelScope.launch {
            MongoDB.getAllDiaries().collect { result ->
                Log.d("Viewmode","$result")
                diaries.value = result
            }
        }
    }
}