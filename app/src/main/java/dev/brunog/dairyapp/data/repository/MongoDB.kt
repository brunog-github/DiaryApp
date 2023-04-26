package dev.brunog.dairyapp.data.repository

import dev.brunog.dairyapp.model.Diary
import dev.brunog.dairyapp.ui.util.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object MongoDB: MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

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
}