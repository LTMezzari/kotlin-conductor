package mezzari.torres.lucas.kotlin_conductor.flow.address

import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.kotlin_conductor.model.Address

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class AddressConductor: BaseConductor() {

    val addresses: ArrayList<Address> = arrayListOf()
    var address: Address? = null

    override fun start() {
        super.start()
        address = null
    }

    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
    }

    override fun nextStep(current: Any, path: Path) {
        super.nextStep(current, path)
    }

    override fun previousStep(current: Any) {
        super.previousStep(current)
    }

    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
    }
}