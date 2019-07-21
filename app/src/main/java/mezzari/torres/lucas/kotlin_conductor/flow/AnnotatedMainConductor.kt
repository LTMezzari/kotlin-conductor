package mezzari.torres.lucas.kotlin_conductor.flow

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
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
object AnnotatedMainConductor: AnnotatedConductor() {

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

    // ------------------------- SplashActivity

    @ConductorAnnotation(SplashActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onSplashActivityNext(splashActivity: SplashActivity) {
        if (!isApplicationAvailable()) {
            startActivity(splashActivity, BlockApplicationActivity::class)
        } else {
            startActivity(splashActivity, LoginActivity::class)
        }
        splashActivity.finish()
    }

    // ------------------------- BlockApplicationActivity

    @ConductorAnnotation(BlockApplicationActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onBlockApplicationActivityNext(splashActivity: SplashActivity) {
        if (isApplicationAvailable()) {
            startActivity(splashActivity, LoginActivity::class)
            splashActivity.finish()
        }
    }

    // ------------------------- LoginActivity

    @ConductorAnnotation(LoginActivity::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onLoginActivityInitiated(loginActivity: LoginActivity) {
        start()
        loginActivity.user = this.user
    }

    @ConductorAnnotation(LoginActivity::class, AnnotatedFlowCycle.STEP_PAUSED)
    private fun onLoginActivityPaused(loginActivity: LoginActivity) {
        this.user = loginActivity.user
        if (this.user.shouldRememberPassword) {
            SessionManager.user = this.user
        } else {
            SessionManager.user = null
        }
    }

    @ConductorAnnotation(LoginActivity::class, AnnotatedFlowCycle.STEP_RESULT)
    private fun onLoginActivityResult(loginActivity: LoginActivity, requestCode: Int, resultCode: Int) {
        if (requestCode == CREATE_ACCOUNT && resultCode == RESULT_OK) {
            loginActivity.user = this.user
        }
    }

    @ConductorAnnotation(LoginActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onLoginActivityNext(loginActivity: LoginActivity, path: Path) {
        if (path == DefaultPath.MAIN) {
            goForward(loginActivity, MainActivity::class)
            end()
        } else {
            startActivityForResult(loginActivity, CreateAccountActivity::class, CREATE_ACCOUNT)
        }
    }

    // ------------------------- CreateAccountActivity

    @ConductorAnnotation(CreateAccountActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onCreateAccountActivityNext(createAccountActivity: CreateAccountActivity) {
        this.user = createAccountActivity.user
        createAccountActivity.setResult(RESULT_OK)
        createAccountActivity.finish()
    }

    @ConductorAnnotation(CreateAccountActivity::class, AnnotatedFlowCycle.PREVIOUS)
    private fun onCreateAccountActivityPrevious(createAccountActivity: CreateAccountActivity) {
        createAccountActivity.setResult(Activity.RESULT_CANCELED)
        createAccountActivity.finish()
    }

    // ------------------------- MainActivity

    @ConductorAnnotation(MainActivity::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onMainActivityInitiated(mainActivity: MainActivity) {
        mainActivity.user = this.user
    }

    @ConductorAnnotation(MainActivity::class, AnnotatedFlowCycle.PREVIOUS)
    private fun onMainActivityPrevious(mainActivity: MainActivity) {
        startActivity(mainActivity, LoginActivity::class)
        mainActivity.finish()
    }

    // ------------------------- Helper Methods

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

    private fun isApplicationAvailable(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        return hour in 9..22 && day in 1..6
    }
}