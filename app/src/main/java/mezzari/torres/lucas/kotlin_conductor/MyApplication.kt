package mezzari.torres.lucas.kotlin_conductor

import android.app.Application
import mezzari.torres.lucas.kotlin_conductor.persisted.SessionManager
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.initialize(this)
        Network.initialize(
            retrofitLevelModules = listOf(GsonConverterModule()),
            okHttpClientLevelModule = listOf(LogModule())
        )
    }
}