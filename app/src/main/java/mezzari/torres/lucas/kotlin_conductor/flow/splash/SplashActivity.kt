package mezzari.torres.lucas.kotlin_conductor.flow.splash

import android.os.Bundle
import android.os.Handler
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.AccessPath
import mezzari.torres.lucas.kotlin_conductor.flow.AnnotatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.ModulatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.SimpleMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.archive.isApplicationAvailable

class SplashActivity : BaseActivity() {
    override val conductor: Conductor = ModulatedMainConductor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler().postDelayed({
            if (isApplicationAvailable()) {
                next()
            } else {
                next(AccessPath.BLOCK)
            }
        }, 2000)
    }
}
