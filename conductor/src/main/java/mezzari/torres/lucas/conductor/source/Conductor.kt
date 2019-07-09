package mezzari.torres.lucas.conductor.source

import mezzari.torres.lucas.conductor.source.path.Path

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Conductor interface to be implemented in the pattern
 **/
interface Conductor {

    fun start()

    fun end()

    fun onStepInitiated(current: Any)

    fun onStepPaused(current: Any)

    fun onStepResult(current: Any, requestCode: Int, resultCode: Int)

    fun nextStep(current: Any, path: Path)

    fun previousStep(current: Any)
}