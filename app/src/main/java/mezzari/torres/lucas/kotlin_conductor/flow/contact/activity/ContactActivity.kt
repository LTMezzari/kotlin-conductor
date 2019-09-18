package mezzari.torres.lucas.kotlin_conductor.flow.contact.activity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_contact.*
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.contact.ContactConductor
import mezzari.torres.lucas.kotlin_conductor.generic.BasePartial

/**
 * @author Lucas T. Mezzari
 * @since 17/09/2019
 */
class ContactActivity: BaseActivity() {
    //Declares the activity conductor
    override val conductor: ContactConductor = ConductorProvider[ContactConductor::class]

    //Declare a helper variable to get the currentPartial
    private val currentPartial: BasePartial? get() {
        return viewFlipper.currentView as? BasePartial
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        //Set the back arrow visible
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //If the user clicked the back arrow
        if (item.itemId == android.R.id.home) {
            //Call the onBackPressed method
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun nextPartial(partial: BasePartial) {
        //Add the new partial to the viewFlipper
        viewFlipper.addView(partial)
        //Show the next view
        viewFlipper.showNext()
    }

    private fun previousPartial() {
        //Check if there is enough children in the viewFlipper
        if (viewFlipper.childCount <= 1) {
            //Return if there is nothing to go back to
            return
        }

        //Run if there is a base partial as the current view
        currentPartial?.run {
            //Call the previous method
            previous()
            //Remove the currentPartial from the viewFlipper
            viewFlipper.removeView(currentPartial)
            //Show the previous partial
            viewFlipper.showPrevious()
        }
    }

    override fun onBackPressed() {
        //Call the previous partial
        previousPartial()
    }
}