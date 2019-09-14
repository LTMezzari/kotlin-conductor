package mezzari.torres.lucas.kotlin_conductor.flow.access

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
import mezzari.torres.lucas.conductor.source.path.DefaultPath
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.kotlin_conductor.flow.access.block.BlockApplicationActivity
import mezzari.torres.lucas.kotlin_conductor.flow.access.create.CreateAccountActivity
import mezzari.torres.lucas.kotlin_conductor.flow.access.home.MainActivity
import mezzari.torres.lucas.kotlin_conductor.flow.access.login.LoginActivity
import mezzari.torres.lucas.kotlin_conductor.flow.access.splash.SplashActivity
import mezzari.torres.lucas.kotlin_conductor.model.User
import mezzari.torres.lucas.kotlin_conductor.persisted.SessionManager
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
class AccessConductor: AnnotatedConductor() {

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
    private fun onSplashActivityNext(splashActivity: SplashActivity, path: Path) {
        if (path == AccessPath.BLOCK) {
            startActivity(splashActivity, BlockApplicationActivity::class)
        } else {
            startActivity(splashActivity, LoginActivity::class)
        }
        splashActivity.finish()
    }

    // ------------------------- BlockApplicationActivity

    @ConductorAnnotation(BlockApplicationActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onBlockApplicationActivityNext(blockApplicationActivity: BlockApplicationActivity) {
        startActivity(blockApplicationActivity, LoginActivity::class)
        blockApplicationActivity.finish()
    }

    // ------------------------- LoginActivity

    @ConductorAnnotation(LoginActivity::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onLoginActivityInitiated(loginActivity: LoginActivity) {
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
            startActivityForResult(loginActivity, CreateAccountActivity::class,
                CREATE_ACCOUNT
            )
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

    enum class AccessPath: Path {
        CREATE_ACCOUNT,
        BLOCK
    }

    companion object {
        private const val CREATE_ACCOUNT = 12
    }
}