package dev.brunog.dairyapp.data.repository

import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<RequestState<Map<LocalDate, List<Diary>>>>
    fun getSelectedDiary(diaryId: ObjectId): Flow<RequestState<Diary>>
    suspend fun insertDiary(diary: Diary): RequestState<Diary>
    suspend fun updateDiary(diary: Diary): RequestState<Diary>
    suspend fun deleteDiary(diaryId: ObjectId): RequestState<Diary>
}