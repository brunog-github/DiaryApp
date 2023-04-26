package dev.brunog.dairyapp.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Diary: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    var _ownerId: String = ""
    var title: String = ""
    var description: String = ""
    var mood: String = Mood.Neutral.name
    var images: RealmList<String> = realmListOf()
    var date: RealmInstant = RealmInstant.from(System.currentTimeMillis(), 0)
}