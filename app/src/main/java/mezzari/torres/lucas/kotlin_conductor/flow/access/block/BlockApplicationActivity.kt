package mezzari.torres.lucas.kotlin_conductor.flow.access.block

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_block_application.*
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.isApplicationAvailable
import mezzari.torres.lucas.kotlin_conductor.flow.access.AccessConductor

class BlockApplicationActivity : BaseActivity() {
    override val conductor: Conductor = ConductorProvider[AccessConductor::class]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_application)
        btnTryAgain.setOnClickListener {
            if (isApplicationAvailable()) {
                next()
            }
        }
    }
}
