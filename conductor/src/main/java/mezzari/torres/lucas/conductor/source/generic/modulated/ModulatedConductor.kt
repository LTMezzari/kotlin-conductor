package mezzari.torres.lucas.conductor.source.generic.modulated

import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.path.Path
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Extends { @link AnnotatedConductor }
 * Conductor that receives a list of { @link ConductorModule } and handle it`s calls
 *
 * @see AnnotatedConductor
 * @see ConductorModule
 **/
abstract class ModulatedConductor: AnnotatedConductor() {

    //Private lazy property that extracts the modules
    private val _modules: HashMap<Class<*>, ConductorModule<*>> by lazy { extractModules() }
    //Abstract property to receive the list of modules
    protected abstract val modules: ArrayList<ConductorModule<*>>

    /**
     * Next Step Function inherited from { @link BaseConductor }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed as the { @link ConductorModule }
     * @param path The path that should be used.
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Path
     */
    override fun nextStep(current: Any, path: Path) {
        super.nextStep(current, path)
        //Calls the nextStep from the module
        _modules[current.javaClass]?.nextStep(current, path)
    }

    /**
     * Previous Step Function inherited from { @link BaseConductor }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed as the { @link ConductorModule }
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Path
     */
    override fun previousStep(current: Any) {
        super.previousStep(current)
        //Calls the previousStep from the module
        _modules[current.javaClass]?.previousStep(current)
    }

    /**
     * On Step Initiated Function inherited from { @link BaseConductor }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed as the { @link ConductorModule }
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Path
     */
    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
        //Calls the onStepInitiated from the module
        _modules[current.javaClass]?.onStepInitiated(current)
    }

    /**
     * On Step Paused Function inherited from { @link BaseConductor }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed as the { @link ConductorModule }
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Path
     */
    override fun onStepPaused(current: Any) {
        super.onStepPaused(current)
        //Calls the onStepPaused from the module
        _modules[current.javaClass]?.onStepPaused(current)
    }

    /**
     * On Step Result Function inherited from { @link BaseConductor }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed as the { @link ConductorModule }
     * @param requestCode The request code of the result
     * @param resultCode The result code of the result
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Path
     */
    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
        //Calls the onStepResult from the module
        _modules[current.javaClass]?.onStepResult(current, requestCode, resultCode)
    }

    /**
     * Private method that extracts the modules and return a HashMap
     */
    private fun extractModules(): HashMap<Class<*>, ConductorModule<*>> {
        //Initializes a HashMap
        val hashMap: HashMap<Class<*>, ConductorModule<*>> = hashMapOf()
        //Loops through the modules
        for (module in modules) {
            //Get the module class
            val mClass: Class<*> = module.javaClass
            //Convert the super of the module class to a parameterized type
            val sType = mClass.genericSuperclass as? ParameterizedType
                ?: throw IllegalArgumentException("The modules should be directly extended from ConductorModule")
            //Get the module type
            val mType = sType.actualTypeArguments[0]
                ?: throw IllegalArgumentException("ConductorModule expects a type parameter")
            //Get the typeClass
            val tClass = mType as? Class<*>
                ?: throw IllegalArgumentException("Cast went wrong when getting the module type class")
            //Add the module to the module type
            hashMap[tClass] = module
        }
        //Return the HashMap
        return hashMap
    }
}