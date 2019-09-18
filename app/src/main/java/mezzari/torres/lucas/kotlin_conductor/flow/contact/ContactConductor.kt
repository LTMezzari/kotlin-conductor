package mezzari.torres.lucas.kotlin_conductor.flow.contact

import android.content.Intent
import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
import mezzari.torres.lucas.conductor.source.generic.modulated.ConductorModule
import mezzari.torres.lucas.conductor.source.generic.modulated.ModulatedConductor
import mezzari.torres.lucas.kotlin_conductor.flow.access.home.MainActivity
import mezzari.torres.lucas.kotlin_conductor.flow.contact.activity.ContactActivity
import mezzari.torres.lucas.kotlin_conductor.flow.contact.first.FirstStepPartial

/**
 * @author Lucas T. Mezzari
 * @since 17/09/2019
 */
class ContactConductor: ModulatedConductor() {
    override val modules: ArrayList<ConductorModule> = arrayListOf(

    )

    // ------------------------------------ MainActivity

    @ConductorAnnotation(MainActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onMainActivityNext(mainActivity: MainActivity) {
        //GO to the next activity
        mainActivity.startActivity(Intent(mainActivity, ContactActivity::class.java))
    }

    // ------------------------------------ ContactActivity

    @ConductorAnnotation(ContactActivity::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onContactActivityInitiated(contactActivity: ContactActivity) {
        //Set the first partial
        contactActivity.nextPartial(FirstStepPartial(contactActivity))
        //Set the activity title
        contactActivity.title = "1"
    }

    // ------------------------------------ ContactActivity

    @ConductorAnnotation(FirstStepPartial::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onFirstStepPartialInitiated(firstStepPartial: FirstStepPartial) {

    }
}