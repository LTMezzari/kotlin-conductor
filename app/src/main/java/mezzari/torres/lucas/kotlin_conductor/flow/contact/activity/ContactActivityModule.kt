package mezzari.torres.lucas.kotlin_conductor.flow.contact.activity

import mezzari.torres.lucas.conductor.source.generic.modulated.ConductorModule
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.flow.contact.ContactConductor
import mezzari.torres.lucas.kotlin_conductor.flow.contact.first.FirstStepPartial

/**
 * @author Lucas T. Mezzari
 * @since 26/09/2019
 */
class ContactActivityModule: ConductorModule<ContactActivity>() {

    private val conductor: ContactConductor get() = ConductorProvider[ContactConductor::class]

    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
        val contactActivity = current as ContactActivity
        //Set the first partial
        contactActivity.nextPartial(FirstStepPartial(contactActivity))
        //Set the activity title
        contactActivity.title = "1"
    }

    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
        val contactActivity = current as ContactActivity
        contactActivity.currentPartial?.onStepResult(requestCode, resultCode)
    }
}