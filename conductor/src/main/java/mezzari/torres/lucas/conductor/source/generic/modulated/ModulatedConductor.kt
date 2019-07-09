package mezzari.torres.lucas.conductor.source.generic.modulated

import mezzari.torres.lucas.conductor.annotation.Module
import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.path.Path
import kotlin.reflect.KClass

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
    private val _modules: HashMap<KClass<*>, ConductorModule> by lazy { extractModules() }
    //Abstract property to receive the list of modules
    protected abstract val modules: ArrayList<ConductorModule>

    /**
     * Next Step Function inherited from { @link BaseConductor }
     * It executes the method nextStep from one of the { @link ConductorModule } annotated with { @link Module }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link Module#step }
     * @param path The path that should be used.
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Module
     * @see Path
     */
    override fun nextStep(current: Any, path: Path) {
        super.nextStep(current, path)
        //Calls the nextStep from the module
        _modules[current::class]?.nextStep(current, path)
    }

    /**
     * Previous Step Function inherited from { @link BaseConductor }
     * It executes the method previousStep from one of the { @link ConductorModule } annotated with { @link Module }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link Module#step }
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Module
     * @see Path
     */
    override fun previousStep(current: Any) {
        super.previousStep(current)
        //Calls the previousStep from the module
        _modules[current::class]?.previousStep(current)
    }

    /**
     * On Step Initiated Function inherited from { @link BaseConductor }
     * It executes the method onStepInitiated from one of the { @link ConductorModule } annotated with { @link Module }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link Module#step }
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Module
     * @see Path
     */
    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
        //Calls the onStepInitiated from the module
        _modules[current::class]?.onStepInitiated(current)
    }

    /**
     * On Step Paused Function inherited from { @link BaseConductor }
     * It executes the method onStepPaused from one of the { @link ConductorModule } annotated with { @link Module }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link Module#step }
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Module
     * @see Path
     */
    override fun onStepPaused(current: Any) {
        super.onStepPaused(current)
        //Calls the onStepPaused from the module
        _modules[current::class]?.onStepPaused(current)
    }

    /**
     * On Step Result Function inherited from { @link BaseConductor }
     * It executes the method onStepResult from one of the { @link ConductorModule } annotated with { @link Module }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link Module#step }
     * @param requestCode The request code of the result
     * @param resultCode The result code of the result
     *
     * @see BaseConductor
     * @see ConductorModule
     * @see Module
     * @see Path
     */
    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
        //Calls the onStepResult from the module
        _modules[current::class]?.onStepResult(current, requestCode, resultCode)
    }

    /**
     * Private method that extracts the modules and return a HashMap
     */
    private fun extractModules(): HashMap<KClass<*>, ConductorModule> {
        //Initializes a HashMap
        val hashMap: HashMap<KClass<*>, ConductorModule> = hashMapOf()
        //Loops through the modules
        for (module in modules) {
            //Get the module class
            val mClass: Class<*> = module.javaClass
            //Get it`s Module annotation
            val annotation: Module = mClass.annotations.find { it is Module } as? Module
                ?: throw RuntimeException("The Module should have a Module annotation")

            //Add the module to the annotation step
            hashMap[annotation.step] = module
        }
        //Return the HashMap
        return hashMap
    }
}