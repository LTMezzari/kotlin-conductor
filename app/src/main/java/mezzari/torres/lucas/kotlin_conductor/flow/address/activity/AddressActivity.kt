package mezzari.torres.lucas.kotlin_conductor.flow.address.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_address.*
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.address.AddressConductor

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class AddressActivity: BaseActivity() {
    override val conductor: AddressConductor = ConductorProvider[AddressConductor::class]

    val navController: NavController by lazy {
        navHost.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}