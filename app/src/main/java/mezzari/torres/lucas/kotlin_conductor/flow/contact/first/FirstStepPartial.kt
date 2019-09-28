package mezzari.torres.lucas.kotlin_conductor.flow.contact.first

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.partial_first_step.view.*
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.observe
import mezzari.torres.lucas.kotlin_conductor.flow.contact.ContactConductor
import mezzari.torres.lucas.kotlin_conductor.generic.BasePartial
import mezzari.torres.lucas.kotlin_conductor.model.Address
import mezzari.torres.lucas.kotlin_conductor.model.Contact

/**
 * @author Lucas T. Mezzari
 * @since 17/09/2019
 */
class FirstStepPartial(context: Context): BasePartial(context) {
    override val conductor: ContactConductor = ConductorProvider[ContactConductor::class]

    var address: Address? = null

    override fun onCreateView(inflater: LayoutInflater?): View {
        //Inflate the layout
        return inflater!!.inflate(R.layout.partial_first_step, this, false)
    }

    override fun onCreate() {
        super.onCreate()

        //Set the observers
        setupObservers(
            etName,
            etAge,
            etNumber,
            etEmail,
            etAddress
        )

        //Set the address click
        etAddress.setOnClickListener {
            next(ContactConductor.ADDRESS_RESULT)
        }

        btnNext.setOnClickListener {
            conductor.contact = Contact().apply {
                //Set the name
                name = etName.text.toString()
                //Set the age
                age = etAge.text.toString().toInt()
                //Set the number
                number = etNumber.text.toString()
                //Set the email
                email = etEmail.text.toString()
                //Set the address
                address = this@FirstStepPartial.address!!
            }
            next()
        }
    }

    private fun setupObservers(vararg fields: EditText) {
        //Loops through the fields
        for (field in fields) {
            //Set the observer in the field
            field.observe {
                //Call the validateFields method
                validateFields()
            }
        }
    }

    private fun validateFields(vararg fields: EditText) {
        //Check if the fields are valid passing the fields parameter
        btnNext.isEnabled = isFieldsValid(*fields)
    }

    private fun isFieldsValid(vararg fields: EditText): Boolean {
        //Loops through the fields
        for (field in fields) {
            //Check if the field is null or empty
            if (field.text.isNullOrEmpty()) {
                //Return that the fields are valid
                return false
            }
        }
        //Return valid if there is a address set
        return address != null
    }
}