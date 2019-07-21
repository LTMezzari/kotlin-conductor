package mezzari.torres.lucas.kotlin_conductor.persisted

import android.content.Context
import android.content.SharedPreferences
import mezzari.torres.lucas.kotlin_conductor.model.User

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
object SessionManager {
    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        this.sharedPreferences = context.getSharedPreferences(javaClass.name, Context.MODE_PRIVATE)
    }

    var user: User get() {
        return sharedPreferences?.run {
            return@run User(
                username = getString("username", "")!!,
                password = getString("password", "")!!,
                shouldRememberPassword = getBoolean("shouldRememberPassword", false)
            )
        } ?: User()
    } set(value) {
        sharedPreferences?.run {
            edit()
                .putString("username", value.username)
                .putString("password", value.password)
                .putBoolean("shouldRememberPassword", value.shouldRememberPassword)
                .apply()
        }
    }
}