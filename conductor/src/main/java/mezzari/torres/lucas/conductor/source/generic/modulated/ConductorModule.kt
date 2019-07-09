package mezzari.torres.lucas.conductor.source.generic.modulated

import mezzari.torres.lucas.conductor.source.path.Path

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * A abstract module to be used in the conductor
 **/
abstract class ConductorModule {

    /**
     * Check { @link Conductor }
     * Next Step function for the Module
     *
     * @param current The current step
     * @param path The path it should use
     *
     * @see mezzari.torres.lucas.conductor.source.Conductor
     */
    open fun nextStep(current: Any, path: Path) {}

    /**
     * Check { @link Conductor }
     * Previous Step function for the Module
     *
     * @param current The current step
     *
     * @see mezzari.torres.lucas.conductor.source.Conductor
     */
    open fun previousStep(current: Any) {}

    /**
     * Check { @link Conductor }
     * On Step Initiated function for the Module
     *
     * @param current The current step
     *
     * @see mezzari.torres.lucas.conductor.source.Conductor
     */
    open fun onStepInitiated(current: Any) {}

    /**
     * Check { @link Conductor }
     * On Step Paused function for the Module
     *
     * @param current The current step
     *
     * @see mezzari.torres.lucas.conductor.source.Conductor
     */
    open fun onStepPaused(current: Any) {}

    /**
     * Check { @link Conductor }
     * On Activity Result function for the Module
     *
     * @param current The current step
     * @param requestCode The request code
     * @param resultCode The result code
     *
     * @see mezzari.torres.lucas.conductor.source.Conductor
     */
    open fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {}
}