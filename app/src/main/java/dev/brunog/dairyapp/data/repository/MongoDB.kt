package dev.brunog.dairyapp.data.repository

import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.util.Constants.APP_ID
import dev.brunog.dairyapp.util.RequestState
import dev.brunog.dairyapp.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import java.time.ZoneId

object MongoDB: MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration
                .Builder(user = user, setOf(Diary::class))
                .initialSubscriptions {sub ->
                    add(
                        query = sub.query<Diary>("ownerId == $0", user.identity),
                        name = "User's Diaries"
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getAllDiaries(): Flow<RequestState<Map<LocalDate, List<Diary>>>> {
        return if (user != null) {
            try {
                realm
                    .query<Diary>("ownerId == $0", user.identity)
                    .sort(property = "date", sortOrder = Sort.DESCENDING)
                    .asFlow()
                    .map { result ->
                        RequestState.Success(
                            data = result.list.groupBy {
                                it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            }
                        )
                    }

            } catch (e: Exception) {
                flow { emit(RequestState.Error(e)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }

    override fun getSelectedDiary(diaryId: ObjectId): Flow<RequestState<Diary>> {
        return if (user != null) {
            try {
                realm.query<Diary>(query = "_id == $0", diaryId).asFlow().map {
                    RequestState.Success(data = it.list.first())
                }
            } catch (e: Exception) {
                flow { emit(RequestState.Error(e)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }

    override suspend fun insertDiary(diary: Diary): RequestState<Diary> {
        return if (user != null) {
            realm.write {
                try {
                    val addDiary = copyToRealm(diary.apply { ownerId = user.id })
                    RequestState.Success(data = addDiary)
                } catch (e: Exception) {
                    RequestState.Error(e)
                }
            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }
    }

    override suspend fun updateDiary(diary: Diary): RequestState<Diary> {
        return if (user != null) {
            realm.write {
                val queriedDiary = query<Diary>(query = "_id == $0", diary._id).first().find()
                if (queriedDiary != null) {
                    queriedDiary.apply {
                        title = diary.title
                        description = diary.description
                        mood = diary.mood
                        images = diary.images
                        date = diary.date
                    }
                    RequestState.Success(data = queriedDiary)
                } else {
                    RequestState.Error(error = Exception("Queried Diary does not exist."))
                }
            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }
    }
}

private class UserNotAuthenticatedException : Exception("User is not Logged in.")