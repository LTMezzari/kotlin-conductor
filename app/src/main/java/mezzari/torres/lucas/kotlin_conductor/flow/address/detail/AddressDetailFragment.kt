package mezzari.torres.lucas.kotlin_conductor.flow.address.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_address_detail.*
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.observe
import mezzari.torres.lucas.kotlin_conductor.flow.address.AddressConductor
import mezzari.torres.lucas.kotlin_conductor.model.Address

/**
 * @author Lucas T. Mezzari
 * @since 15/09/2019
 */
class AddressDetailFragment: BaseFragment() {
    override val conductor: AddressConductor = ConductorProvider[AddressConductor::class]

    //Declare a Address object
    var address: Address? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout
        return inflater.inflate(R.layout.fragment_address_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set the address values to the given fields
        address?.run {
            etCep.setText(cep)
            etComplement.setText(complement)
            etNeighborhood.setText(neighborhood)
            etState.setText(state)
            etStateDescription.setText(stateDescription)
            etStreet.setText(street)
            etUnity.setText(unity)
        }

        //Bind the observer into the edit text
        bindFields(
            etCep,
            etComplement,
            etNeighborhood,
            etState,
            etStateDescription,
            etStreet,
            etUnity
        )

        //Bind the on click to the save button
        btnSave.setOnClickListener {
            //Get the field values
            val cep = etCep.text.toString()
            val complement = etComplement.text.toString()
            val neighborhood = etNeighborhood.text.toString()
            val state = etState.text.toString()
            val stateDescription = etStateDescription.text.toString()
            val street = etStreet.text.toString()
            val unity = etUnity.text.toString()

            //Update into the address
            address?.run {
                this.cep = cep
                this.complement = complement
                this.neighborhood = neighborhood
                this.state = state
                this.stateDescription = stateDescription
                this.street = street
                this.unity = unity
                //Go to the next step
                next()
            }
        }
    }

    private fun bindFields(vararg fields: EditText) {
        //Loops through the fields
        for (field in fields) {
            //Observe the field
            field.observe {
                //Update the button state
                btnSave.isEnabled = isFieldsValid()
            }
        }
    }

    private fun isFieldsValid(): Boolean {
        //Check if everything was filled
        return !etCep.text.isNullOrEmpty() &&
                !etComplement.text.isNullOrEmpty() &&
                !etNeighborhood.text.isNullOrEmpty() &&
                !etState.text.isNullOrEmpty() &&
                !etStateDescription.text.isNullOrEmpty() &&
                !etStreet.text.isNullOrEmpty() &&
                !etUnity.text.isNullOrEmpty()
    }
}