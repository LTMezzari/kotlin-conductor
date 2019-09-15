package mezzari.torres.lucas.kotlin_conductor.flow.address

import android.content.Intent
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.navController
import mezzari.torres.lucas.kotlin_conductor.flow.access.home.MainActivity
import mezzari.torres.lucas.kotlin_conductor.flow.address.activity.AddressActivity
import mezzari.torres.lucas.kotlin_conductor.flow.address.detail.AddressDetailFragment
import mezzari.torres.lucas.kotlin_conductor.flow.address.list.ListAddressesFragment
import mezzari.torres.lucas.kotlin_conductor.flow.address.search.SearchAddressFragment
import mezzari.torres.lucas.kotlin_conductor.model.Address

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class AddressConductor: BaseConductor() {

    //Declares a list of addresses
    private val addresses: ArrayList<Address> = arrayListOf()
    //Declare a address variable to handle registration and update
    var address: Address? = null
    //Declare a variable responsible for flow control
    var currentFragment: BaseFragment? = null

    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
        //Check witch step is the current
        when (current) {
            is ListAddressesFragment -> {
                //Update the current fragment
                currentFragment = current
                //Set the current addresses
                current.addressess = addresses
                //Check if there is a address to update
                current.put(address)
            }

            is SearchAddressFragment -> {
                //Update the current fragment
                currentFragment = current
            }

            is AddressDetailFragment -> {
                //Update the current fragment
                currentFragment = current
                //Set the current address
                current.address = address
            }
        }
    }

    override fun onStepPaused(current: Any) {
        super.onStepPaused(current)
        //Check if the step was AddressDetail
        if (current is AddressDetailFragment) {
            //Set the address to the modified
            address = current.address
        }
    }

    override fun nextStep(current: Any, path: Path) {
        super.nextStep(current, path)
        //Check witch step is
        when (current) {
            is MainActivity -> {
                //Go to AddressActivity
                current.startActivity(Intent(current, AddressActivity::class.java))
            }

            is AddressActivity -> {
                //Navigate to the search fragment
                current.navController.navigate(R.id.searchAddressFragment)
            }

            is ListAddressesFragment, is SearchAddressFragment -> {
                //Navigate to the address detail fragment
                (current as Fragment).navController.navigate(R.id.addressDetailFragment)
            }

            is AddressDetailFragment -> {
                //Navigate to list fragment
                current.navController.popBackStack(R.id.listAddressesFragment, false)
            }
        }
    }

    override fun previousStep(current: Any) {
        super.previousStep(current)
        //Check if the step is the SearchAddressFragment
        if (current is SearchAddressFragment) {
            //Clear the address
            address = null
        }
    }
}