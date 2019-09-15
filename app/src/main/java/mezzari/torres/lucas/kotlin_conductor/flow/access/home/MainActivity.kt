package mezzari.torres.lucas.kotlin_conductor.flow.access.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.access.AccessConductor
import mezzari.torres.lucas.kotlin_conductor.flow.address.AddressConductor
import mezzari.torres.lucas.kotlin_conductor.model.User

class MainActivity : BaseActivity() {
    override val conductor: Conductor = ConductorProvider[AccessConductor::class]

    private lateinit var _user: User
    var user: User get() {
        return _user
    } set(value) {
        _user = value
        tvWelcome.text = "Hello, ${value.username}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflate the menu
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Check the item id
        when (item.itemId) {
            R.id.menuAddress -> {
                //Call the nextStep from the AddressConductor
                next(conductor = ConductorProvider[AddressConductor::class])
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
        previous()
    }
}
