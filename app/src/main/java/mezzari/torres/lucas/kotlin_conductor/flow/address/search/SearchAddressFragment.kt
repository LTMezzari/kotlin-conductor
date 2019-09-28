package mezzari.torres.lucas.kotlin_conductor.flow.address.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_search_address.*
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.observe
import mezzari.torres.lucas.kotlin_conductor.flow.address.AddressConductor
import mezzari.torres.lucas.kotlin_conductor.service.ViacepService

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class SearchAddressFragment: BaseFragment() {
    override val conductor: AddressConductor = ConductorProvider[AddressConductor::class]

    //Creates a ViacepService instance
    private val service: ViacepService = ViacepService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout
        return inflater.inflate(R.layout.fragment_search_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Observe the etSearch
        etSearch.observe {
            //Update the search button state
            btnSearch.isEnabled = !it.isNullOrEmpty()
        }

        //Bind the btnSearch click to the service call
        btnSearch.setOnClickListener {
            //Get the search field text
            val cep = etSearch.text.toString()
            //Show the loader
            flLoader.visibility = View.VISIBLE
            //Call the getAddress passing the cep
            service.getAddress(cep).then { response ->
                //Hide the loader
                flLoader.visibility = View.GONE
                //Check the response nullability
                response?.run {
                    //Save the address object into the conductor
                    conductor.address = this
                    //Go to the next step
                    next()
                }
            }.catch { error ->
                //Hide the loader
                flLoader.visibility = View.GONE
                //Call a error toast
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}