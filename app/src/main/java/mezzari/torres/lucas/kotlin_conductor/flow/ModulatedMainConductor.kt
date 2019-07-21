package mezzari.torres.lucas.kotlin_conductor.flow

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
import mezzari.torres.lucas.conductor.source.generic.modulated.ConductorModule
import mezzari.torres.lucas.conductor.source.generic.modulated.ModulatedConductor
import mezzari.torres.lucas.conductor.source.path.DefaultPath
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.kotlin_conductor.flow.block.BlockApplicationActivity
import mezzari.torres.lucas.kotlin_conductor.flow.create.CreateAccountActivity
import mezzari.torres.lucas.kotlin_conductor.flow.home.MainActivity
import mezzari.torres.lucas.kotlin_conductor.flow.login.LoginActivity
import mezzari.torres.lucas.kotlin_conductor.flow.login.LoginModule
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
object ModulatedMainConductor: ModulatedConductor() {
    override val modules: ArrayList<ConductorModule> = arrayListOf(
        LoginModule
    )

    const val CREATE_ACCOUNT = 12

    lateinit var user: User

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
    private fun onBlockApplicationActivityNext(blockApplicationActivity: BlockApplicationActivity) {
        if (isApplicationAvailable()) {
            startActivity(blockApplicationActivity, LoginActivity::class)
            blockApplicationActivity.finish()
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

    fun goForward(current: AppCompatActivity, activity: KClass<*>) {
        this.activities += WeakReference(current)
        startActivity(current, activity)
    }

    fun startActivity(current: AppCompatActivity, activity: KClass<*>) {
        current.startActivity(Intent(current, activity.java))
    }

    fun startActivityForResult(current: AppCompatActivity, activity: KClass<*>, requestCode: Int) {
        current.startActivityForResult(Intent(current, activity.java), requestCode)
    }

    fun isApplicationAvailable(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        return hour in 9..17 && day in 2..6
    }
}