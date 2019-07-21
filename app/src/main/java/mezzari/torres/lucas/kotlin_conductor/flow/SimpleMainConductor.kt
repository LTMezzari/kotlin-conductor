package mezzari.torres.lucas.kotlin_conductor.flow

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import mezzari.torres.lucas.conductor.source.path.DefaultPath
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.kotlin_conductor.flow.block.BlockApplicationActivity
import mezzari.torres.lucas.kotlin_conductor.flow.create.CreateAccountActivity
import mezzari.torres.lucas.kotlin_conductor.flow.home.MainActivity
import mezzari.torres.lucas.kotlin_conductor.flow.login.LoginActivity
import mezzari.torres.lucas.kotlin_conductor.flow.splash.SplashActivity
import mezzari.torres.lucas.kotlin_conductor.model.User
import mezzari.torres.lucas.kotlin_conductor.persisted.SessionManager
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
object SimpleMainConductor: BaseConductor() {

    private const val CREATE_ACCOUNT = 12

    private lateinit var user: User

    private val activities: ArrayList<WeakReference<AppCompatActivity>> = arrayListOf()

    override fun start() {
        super.start()
        this.user = SessionManager.user ?: User()
    }

    override fun end() {
        super.end()
        for(activity in activities) {
            activity.get()?.finish()
        }
        activities.clear()
    }

    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
        when (current) {
            is LoginActivity -> {
                start()
                current.user = this.user
            }

            is MainActivity -> {
                current.user = this.user
            }
        }
    }

    override fun onStepPaused(current: Any) {
        super.onStepPaused(current)
        when (current) {
            is LoginActivity -> {
                this.user = current.user
                if (this.user.shouldRememberPassword) {
                    SessionManager.user = this.user
                } else {
                    SessionManager.user = null
                }
            }
        }
    }

    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
        if (requestCode == CREATE_ACCOUNT && resultCode == RESULT_OK) {
            val loginActivity = current as LoginActivity
            loginActivity.user = this.user
        }
    }

    override fun nextStep(current: Any, path: Path) {
        super.nextStep(current, path)
        when (current) {
            is SplashActivity -> {
                if (path == AccessPath.BLOCK) {
                    startActivity(current, BlockApplicationActivity::class)
                } else {
                    startActivity(current, LoginActivity::class)
                }
                current.finish()
            }

            is BlockApplicationActivity -> {
                startActivity(current, LoginActivity::class)
                current.finish()
            }

            is LoginActivity -> {
                if (path == DefaultPath.MAIN) {
                    goForward(current, MainActivity::class)
                    end()
                } else {
                    startActivityForResult(current, CreateAccountActivity::class, CREATE_ACCOUNT)
                }
            }

            is CreateAccountActivity -> {
                this.user = current.user
                current.setResult(RESULT_OK)
                current.finish()
            }
        }
    }

    override fun previousStep(current: Any) {
        super.previousStep(current)
        when (current) {
            is MainActivity -> {
                startActivity(current, LoginActivity::class)
                current.finish()
            }

            is CreateAccountActivity -> {
                current.setResult(RESULT_CANCELED)
                current.finish()
            }
        }
    }

    private fun goForward(current: AppCompatActivity, activity: KClass<*>) {
        this.activities += WeakReference(current)
        startActivity(current, activity)
    }

    private fun startActivity(current: AppCompatActivity, activity: KClass<*>) {
        current.startActivity(Intent(current, activity.java))
    }

    private fun startActivityForResult(current: AppCompatActivity, activity: KClass<*>, requestCode: Int) {
        current.startActivityForResult(Intent(current, activity.java), requestCode)
    }
}