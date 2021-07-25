package com.ufonaut.test.viewmodel

import android.location.Location
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.ufonaut.test.model.api.PlacesApi
import com.ufonaut.test.model.api.PlacesApiImpl
import com.ufonaut.test.model.api.PlacesCallback
import com.ufonaut.test.model.dao.PlacesDao
import com.ufonaut.test.model.dao.PlacesDaoImpl
import com.ufonaut.test.model.dto.Place
import com.ufonaut.test.model.dto.RealmLiveData
import io.realm.Realm
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel(),
    LifecycleObserver, PlacesCallback {

    private val realm by lazy {
        Realm.getDefaultInstance()
    }

    private val dao: PlacesDao by lazy {
        PlacesDaoImpl(realm)
    }

    private val api: PlacesApi by lazy {
        PlacesApiImpl()
    }

    fun observePlaces(): RealmLiveData<Place> {
        return dao.observePlaces()
    }

    fun refreshPlaces(location: Location?) {
        location?.let {
            api.getNearestPlaces(location, this)
        }
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

    override fun onPlacesReceived(places: List<Place>) {
        dao.savePlaces(places)
    }
}