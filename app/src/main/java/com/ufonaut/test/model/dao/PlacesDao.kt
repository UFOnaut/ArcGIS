package com.ufonaut.test.model.dao

import com.ufonaut.test.Constants
import com.ufonaut.test.model.dto.Place
import com.ufonaut.test.model.dto.RealmLiveData
import io.realm.Realm
import io.realm.Sort

class PlacesDaoImpl(private val realm: Realm): PlacesDao {

    override fun observePlaces(): RealmLiveData<Place> {
        return RealmLiveData(realm.where(Place::class.java).limit(Constants.RESULTS_NUMBER).sort(Constants.RESULTS_SORT_FIELD, Sort.DESCENDING).findAllAsync())
    }

    override fun savePlaces(places: List<Place>) {
        realm.executeTransactionAsync {
            it.insertOrUpdate(places)
        }
    }
}

interface PlacesDao {
    fun observePlaces(): RealmLiveData<Place>
    fun savePlaces(places: List<Place>)
}