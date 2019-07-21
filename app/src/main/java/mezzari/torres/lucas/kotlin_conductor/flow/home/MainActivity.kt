package mezzari.torres.lucas.kotlin_conductor.flow.home

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.AnnotatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.ModulatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.SimpleMainConductor
import mezzari.torres.lucas.kotlin_conductor.model.User

class MainActivity : BaseActivity() {
    override val conductor: Conductor = ModulatedMainConductor

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        previous()
    }
}
