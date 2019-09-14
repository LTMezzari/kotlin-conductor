package mezzari.torres.lucas.kotlin_conductor.flow.address.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_addresses.*
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.adapter.AddressAdapter
import mezzari.torres.lucas.kotlin_conductor.flow.address.AddressConductor
import mezzari.torres.lucas.kotlin_conductor.model.Address

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class ListAddressesFragment: BaseFragment() {
    override val conductor: AddressConductor = ConductorProvider[AddressConductor::class]

    private val adapter: AddressAdapter by lazy {
        AddressAdapter(context!!).apply {
            onClickListener = {
                conductor.address = it
                next()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_addresses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAddresses.apply {
            layoutManager = LinearLayoutManager(this@ListAddressesFragment.context, RecyclerView.VERTICAL, false)
            adapter = this@ListAddressesFragment.adapter
        }
    }

    fun put(address: Address?) {
        adapter.put(address)
    }
}