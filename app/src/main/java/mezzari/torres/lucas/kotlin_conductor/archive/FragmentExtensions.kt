package mezzari.torres.lucas.kotlin_conductor.archive

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

/**
 * @author Lucas T. Mezzari
 * @since 15/09/2019
 */
val Fragment.navController: NavController get() {
    //Get the navController
    return findNavController()
}