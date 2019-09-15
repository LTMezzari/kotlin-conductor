package mezzari.torres.lucas.kotlin_conductor.flow.address.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        //GEt the navController
        navHost.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        //Set the navigation arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflate the menu
        menuInflater.inflate(R.menu.toolbar_address, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Check the item id
        when (item.itemId) {
            R.id.menuSearch -> {
                //Go forward
                next()
                return true
            }

            android.R.id.home -> {
                //Call the onBackPressed method
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        //Notify the conductor
        conductor.currentFragment?.previous()
        super.onBackPressed()
    }
}