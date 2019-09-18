package mezzari.torres.lucas.kotlin_conductor.flow.contact.first

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.partial_first_step.view.*
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.contact.ContactConductor
import mezzari.torres.lucas.kotlin_conductor.generic.BasePartial

/**
 * @author Lucas T. Mezzari
 * @since 17/09/2019
 */
class FirstStepPartial(context: Context): BasePartial(context) {
    override val conductor: ContactConductor = ConductorProvider[ContactConductor::class]

    override fun onCreateView(inflater: LayoutInflater?): View {
        //Inflate the layout
        return inflater!!.inflate(R.layout.partial_first_step, this, false)
    }

    override fun onCreate() {
        super.onCreate()
        btnNext.setOnClickListener {
            next()
        }
    }
}