package mezzari.torres.lucas.kotlin_conductor.archive

import java.util.*

/**
 * @author Lucas T. Mezzari
 * @since 21/07/2019
 **/
fun isApplicationAvailable(): Boolean {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val day = calendar.get(Calendar.DAY_OF_WEEK)
    return hour in 9..17 && day in 2..6
}
