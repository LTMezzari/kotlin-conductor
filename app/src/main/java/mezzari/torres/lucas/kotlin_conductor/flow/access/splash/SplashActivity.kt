package mezzari.torres.lucas.kotlin_conductor.flow.access.splash

import android.os.Bundle
import android.os.Handler
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.isApplicationAvailable
import mezzari.torres.lucas.kotlin_conductor.flow.access.AccessConductor

class SplashActivity : BaseActivity() {
    override val conductor: Conductor = ConductorProvider[AccessConductor::class]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler().postDelayed({
            if (isApplicationAvailable()) {
                next()
            } else {
                next(AccessConductor.AccessPath.BLOCK)
            }
        }, 2000)
    }
}
