package mezzari.torres.lucas.kotlin_conductor.flow.block

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_block_application.*
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.AnnotatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.ModulatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.SimpleMainConductor

class BlockApplicationActivity : BaseActivity() {
    override val conductor: Conductor = ModulatedMainConductor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_application)
        btnTryAgain.setOnClickListener {
            next()
        }
    }
}
