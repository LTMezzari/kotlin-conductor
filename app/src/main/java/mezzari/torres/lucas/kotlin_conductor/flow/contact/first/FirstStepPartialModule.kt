package mezzari.torres.lucas.kotlin_conductor.flow.contact.first

import android.app.Activity.RESULT_OK
import android.content.Intent
import mezzari.torres.lucas.conductor.source.generic.modulated.ConductorModule
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.flow.address.AddressConductor
import mezzari.torres.lucas.kotlin_conductor.flow.address.activity.AddressActivity
import mezzari.torres.lucas.kotlin_conductor.flow.contact.ContactConductor

/**
 * @author Lucas T. Mezzari
 * @since 26/09/2019
 */
class FirstStepPartialModule: ConductorModule<FirstStepPartial>() {
    override fun nextStep(current: Any, path: Int) {
        super.nextStep(current, path)
        val firstStepPartial = current as FirstStepPartial
        firstStepPartial.parentActivity.startActivityForResult(Intent(
            firstStepPartial.context,
            AddressActivity::class.java
        ), path)
    }

    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
        val firstStepPartial = current as FirstStepPartial
        if (requestCode == ContactConductor.ADDRESS_RESULT && resultCode == RESULT_OK) {
            val addressConductor: AddressConductor = ConductorProvider[AddressConductor::class]
            firstStepPartial.address = addressConductor.address
        }
    }
}