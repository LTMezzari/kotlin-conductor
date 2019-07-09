package mezzari.torres.lucas.conductor.source.generic.annotated

import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.BaseConductor
import mezzari.torres.lucas.conductor.source.path.Path
import java.lang.reflect.Method
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Extends { @link BaseConductor }
 * Conductor that handles methods with the { @link ConductorAnnotation }
 *
 * @see BaseConductor
 * @see mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
 **/
abstract class AnnotatedConductor: BaseConductor() {

    //Private lazy property that is used to invoke the conductor methods
    private val annotatedMethodsManager: HashMap<KClass<*>, AnnotatedMethodsManager> by lazy { extractMethods() }

    /**
     * Next Step Function inherited from { @link BaseConductor }
     * It executes a declared method annotated with { @link ConductorAnnotation }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link ConductorAnnotation#step }
     * @param path The path that should be used.
     *
     * @see BaseConductor
     * @see ConductorAnnotation
     * @see ConductorAnnotation#step
     * @see Path
     */
    override fun nextStep(current: Any, path: Path) {
        super.nextStep(current, path)
        //Invoke the annotated method with the current step class
        annotatedMethodsManager[current::class]?.invokeNextStepMethod(this, current, path)
    }

    /**
     * Previous Step Function inherited from { @link BaseConductor }
     * It executes a declared method annotated with { @link ConductorAnnotation }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link ConductorAnnotation#step }
     *
     * @see BaseConductor
     * @see ConductorAnnotation
     * @see ConductorAnnotation#step
     * @see Path
     */
    override fun previousStep(current: Any) {
        super.previousStep(current)
        //Invoke the annotated method with the current step class
        annotatedMethodsManager[current::class]?.invokePreviousStepMethod(this, current)
    }

    /**
     * On Step Initiated Function inherited from { @link BaseConductor }
     * It executes a declared method annotated with { @link ConductorAnnotation }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link ConductorAnnotation#step }
     *
     * @see BaseConductor
     * @see ConductorAnnotation
     * @see ConductorAnnotation#step
     * @see Path
     */
    override fun onStepInitiated(current: Any) {
        super.onStepInitiated(current)
        //Invoke the annotated method with the current step class
        annotatedMethodsManager[current::class]?.invokeOnStepInitiatedMethod(this, current)

    }

    /**
     * On Step Paused Function inherited from { @link BaseConductor }
     * It executes a declared method annotated with { @link ConductorAnnotation }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link ConductorAnnotation#step }
     *
     * @see BaseConductor
     * @see ConductorAnnotation
     * @see ConductorAnnotation#step
     * @see Path
     */
    override fun onStepPaused(current: Any) {
        super.onStepPaused(current)
        //Invoke the annotated method with the current step class
        annotatedMethodsManager[current::class]?.invokeOnStepPausedMethod(this, current)
    }

    /**
     * On Step Result Function inherited from { @link BaseConductor }
     * It executes a declared method annotated with { @link ConductorAnnotation }
     *
     * @param current The current step of the conductor. The class should be equal to the class passed to { @link ConductorAnnotation#step }
     * @param requestCode The request code of the result
     * @param resultCode The result code of the result
     *
     * @see BaseConductor
     * @see ConductorAnnotation
     * @see ConductorAnnotation#step
     * @see Path
     */
    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        super.onStepResult(current, requestCode, resultCode)
        //Invoke the annotated method with the current step class
        annotatedMethodsManager[current::class]?.invokeOnActivityResultMethod(this, current, requestCode, resultCode)
    }

    /**
     * Method that loops through all declared methods of the class and extracts the ones that should be called in it`s cycle
     *
     * @return A HashMap of a Kotlin Class and { @link AnnotatedMethodsManager }
     *
     * @see AnnotatedMethodsManager
     */
    private fun extractMethods(): HashMap<KClass<*>, AnnotatedMethodsManager> {
        //Get the declared methods
        val methods: Array<Method> = javaClass.declaredMethods
        //Instantiate a hash map of KClass and AnnotatedMethodsManager
        val hashMap: HashMap<KClass<*>, AnnotatedMethodsManager> = hashMapOf()
        //Loops through the methods
        for (method in methods) {
            //Check if the method has a ConductorAnnotation
            val conductorAnnotation: ConductorAnnotation? = method.annotations.find { it is ConductorAnnotation } as? ConductorAnnotation
            //Run if a ConductorAnnotation was found
            conductorAnnotation?.run {
                //Check if map has a key with the step class
                //If it has not, creates a new AnnotatedMethodsManager
                val methodManager: AnnotatedMethodsManager = hashMap[step] ?: AnnotatedMethodsManager()

                //Check in what cycle the method should be called
                when (managerCycle) {
                    //When Next
                    AnnotatedFlowCycle.NEXT -> {
                        //Set the manager next method
                        methodManager.nextStepMethod = method
                    }

                    //When Previous
                    AnnotatedFlowCycle.PREVIOUS -> {
                        //Set the manager previous method
                        methodManager.previousStepMethod = method
                    }

                    //When Initiated
                    AnnotatedFlowCycle.STEP_INITIATED -> {
                        //Set the manager initiated method
                        methodManager.onStepInitiatedMethod = method
                    }

                    //When Paused
                    AnnotatedFlowCycle.STEP_PAUSED -> {
                        //Set the manager paused method
                        methodManager.onStepPausedMethod = method
                    }

                    //When result
                    AnnotatedFlowCycle.STEP_RESULT -> {
                        //Set the manager result method
                        methodManager.onActivityResultMethod = method
                    }
                }

                //Update the hash map
                hashMap[step] = methodManager
            }
        }
        //Return the declared hash map
        return hashMap
    }

    /**
     * Private Class that holds and manage the conductor methods
     */
    private class AnnotatedMethodsManager {

        //Optional Method for nextStep
        var nextStepMethod: Method? = null

        //Optional Method for previousStep
        var previousStepMethod: Method? = null

        //Optional Method for onStepInitiated
        var onStepInitiatedMethod: Method? = null

        //Optional Method for onStepPaused
        var onStepPausedMethod: Method? = null

        //Optional Method for onStepResult
        var onActivityResultMethod: Method? = null

        /**
         * Method that invokes the OnStepPaused method
         *
         * @param conductor The conductor instance for a Reflect Invocation
         * @param current The Current Step of the conductor
         * @param path The Path of the next step
         */
        fun invokeNextStepMethod(conductor: Conductor, current: Any, path: Path) {
            //Call the invoke method that setup the method invocation
            invokeMethod(nextStepMethod) {
                //If the method has two parameter types, invoke passing the step and path
                if (it.parameterTypes.size == 2) {
                    //Invoke the method of the conductor instance, step, and path
                    it.invoke(conductor, it.parameterTypes[0].cast(current), it.parameterTypes[1].cast(path))
                } else if (it.parameterTypes.size == 1) {
                    //Invoke the method with the conductor instance, and step
                    it.invoke(conductor, it.parameterTypes[0].cast(current))
                }
            }
        }

        /**
         * Method that invokes the PreviousStep method
         *
         * @param conductor The conductor instance for a Reflect Invocation
         * @param current The Current Step of the conductor
         */
        fun invokePreviousStepMethod(conductor: Conductor, current: Any) {
            //Call the invoke method that setup the method invocation
            invokeMethod(previousStepMethod) {
                //Invoke the method with the conductor instance, and the step
                it.invoke(conductor, it.parameterTypes[0].cast(current))
            }
        }

        /**
         * Method that invokes the OnStepInitiated method
         *
         * @param conductor The conductor instance for a Reflect Invocation
         * @param current The Current Step of the conductor
         */
        fun invokeOnStepInitiatedMethod(conductor: Conductor, current: Any) {
            //Call the invoke method that setup the method invocation
            invokeMethod(onStepInitiatedMethod) {
                //Invoke the method with the conductor instance, and the step
                it.invoke(conductor, it.parameterTypes[0].cast(current))
            }
        }

        /**
         * Method that invokes the OnStepPaused method
         *
         * @param conductor The conductor instance for a Reflect Invocation
         * @param current The Current Step of the conductor
         */
        fun invokeOnStepPausedMethod(conductor: Conductor, current: Any) {
            //Call the invoke method that setup the method invocation
            invokeMethod(onStepPausedMethod) {
                //Invoke the method with the conductor instance, and the step
                it.invoke(conductor, it.parameterTypes[0].cast(current))
            }
        }

        /**
         * Method that invokes the OnActivityResult method
         *
         * @param conductor The conductor instance for a Reflect Invocation
         * @param current The Current Step of the conductor
         * @param requestCode The Request Code of the result
         * @param resultCode The Result Code of the result
         */
        fun invokeOnActivityResultMethod(conductor: Conductor, current: Any, requestCode: Int, resultCode: Int) {
            //Call the invoke method that setup the method invocation
            invokeMethod(onActivityResultMethod) {
                //Validate if the method has all the required parameters
                if (it.parameterTypes.size == 3) {
                    //Invoke the method with the conductor instance, and the step
                    it.invoke(conductor, it.parameterTypes[0].cast(current), requestCode, resultCode)
                }
            }
        }

        /**
         * Method that set up the method invocation
         *
         * @param method The method that will be invoked
         * @param block A closure to the method invocation
         */
        private fun invokeMethod(method: Method?, block: (method: Method) -> Unit) {
            //Do a null safety validation
            method?.run {
                //Get the method accessibility
                val wasAccessible: Boolean = isAccessible
                //Try to run the closure
                try {
                    //Set it`s accessibility to true
                    isAccessible = true
                    //Run the closure
                    block(this)
                } finally {
                    //Set it`s accessibility back to normal
                    isAccessible = wasAccessible
                }
            }
        }
    }
}