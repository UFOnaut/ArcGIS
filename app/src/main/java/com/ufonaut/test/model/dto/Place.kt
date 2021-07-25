package com.ufonaut.test.model.dto

import io.realm.RealmObject
import io.realm.annotations.Index

import io.realm.annotations.PrimaryKey
import java.util.*


open class Place: RealmObject() {
    var title: String? = null
    var lat: Double? = null
    var lng: Double? = null
    var createdAt: Date? = null

    init {
        createdAt = Date()
    }
}