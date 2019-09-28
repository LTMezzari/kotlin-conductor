package mezzari.torres.lucas.conductor.source.generic.provider

import android.util.ArrayMap
import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import java.lang.reflect.Constructor
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 13/09/2019
 *
 * A ConductorProvider is a singleton that will manage the conductors lifecycle.
 * Using a provider will enable the garbage collector to destroy the instances whenever
 * a conductor calls it end() function, and will call it start() function
 * whenever a new instance is created.
 */
object ConductorProvider {

    //Declares a array map of conductors
    //This will be used to store and manage the conductor instances
    val conductors: ArrayMap<KClass<*>, BaseConductor> = ArrayMap()

    val onConductorEnded: BaseConductor.() -> Unit = {
        //Remove the conductor from the map
        //This will enable the garbage collector to destroy the instance
        conductors.remove(this::class, this)
    }

    inline operator fun <reified T : BaseConductor> get(conductorClass: KClass<*>): T {
        //Try to find the conductor inside the array map
        val conductor = conductors[conductorClass]
        //If there is no conductor inside, instantiate another one
        if (conductor == null) {
            //Get a empty declared constructor from the class or throw an Exception
            val constructor =
                conductorClass.java.constructors.first { it.parameters.isEmpty() }
                    ?: throw IllegalArgumentException("The Conductor should contain a empty constructor")
            //Creates a new instance from the constructor
            val newConductor = constructor.newInstance() as T
            //Run the needed code inside the newConductor
            newConductor.run {
                //Save it into the array map
                conductors[conductorClass] = newConductor
                //Set the onConductorEndedListener
                onConductorEndedListener = onConductorEnded
                //Start the instance
                newConductor.start()
            }
            //Return the new conductor
            return newConductor
        }
        //Return the found conductor
        return conductor as T
    }

    operator fun <T: BaseConductor> set(conductorClass: KClass<*>, conductor: T) {
        //Start the conductor
        conductor.start()
        //Set the conductor into the map
        conductors[conductorClass] = conductor
    }
}