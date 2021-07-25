package com.ufonaut.test

import android.app.Application
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class MapApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initRealm()
        ArcGISRuntimeEnvironment.setApiKey("AAPK1c07a3184c5e4b36830a838df6f4d68cWXCn4BPPAffM7Lg9k5vvq4Zm7kvSoHoInEAUMTRqKX76BwHHc2HpnaGkTcmvo20W")
    }

    private fun initRealm() {
        Realm.init(this)
        val schemaVersion = 1
        val realmConfig = RealmConfiguration.Builder()
            .schemaVersion(schemaVersion.toLong())
            .migration { _: DynamicRealm?, _: Long, _: Long -> }
            .deleteRealmIfMigrationNeeded()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}