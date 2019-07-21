package mezzari.torres.lucas.kotlin_conductor

import android.app.Application
import mezzari.torres.lucas.kotlin_conductor.persisted.SessionManager

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.initialize(this)
    }
}