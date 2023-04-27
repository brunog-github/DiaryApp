package dev.brunog.dairyapp.data.repository

import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<RequestState<Map<LocalDate, List<Diary>>>>
}