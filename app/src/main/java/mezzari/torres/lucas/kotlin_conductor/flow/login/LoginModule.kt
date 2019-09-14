package mezzari.torres.lucas.kotlin_conductor.flow.login

import android.app.Activity
import mezzari.torres.lucas.conductor.annotation.Module
import mezzari.torres.lucas.conductor.source.generic.modulated.ConductorModule
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.conductor.source.path.DefaultPath
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.kotlin_conductor.flow.ModulatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.create.CreateAccountActivity
import mezzari.torres.lucas.kotlin_conductor.flow.home.MainActivity
import mezzari.torres.lucas.kotlin_conductor.persisted.SessionManager

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
@Module(LoginActivity::class)
object LoginModule: ConductorModule() {
    
    private val conductor: ModulatedMainConductor get() {
        return ConductorProvider[ModulatedMainConductor::class]
    }
    
    override fun onStepInitiated(current: Any) {
        val loginActivity = current as LoginActivity
        loginActivity.user = conductor.user
    }

    override fun onStepPaused(current: Any) {
        val loginActivity = current as LoginActivity
        conductor.user = loginActivity.user
        if (conductor.user.shouldRememberPassword) {
            SessionManager.user = conductor.user
        } else {
            SessionManager.user = null
        }
    }

    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        val loginActivity = current as LoginActivity
        if (requestCode == ModulatedMainConductor.CREATE_ACCOUNT && resultCode == Activity.RESULT_OK) {
            loginActivity.user = conductor.user
        }
    }

    override fun nextStep(current: Any, path: Path) {
        val loginActivity = current as LoginActivity
        if (path == DefaultPath.MAIN) {
            conductor.goForward(loginActivity, MainActivity::class)
            conductor.end()
        } else {
            conductor.startActivityForResult(
                loginActivity,
                CreateAccountActivity::class,
                ModulatedMainConductor.CREATE_ACCOUNT
            )
        }
    }
}