package mezzari.torres.lucas.conductor.source.generic

import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.path.Path

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Abstract Conductor that implements some validations
 **/
abstract class BaseConductor: Conductor {

    private var isStarted: Boolean = false

    /**
     * Method used to set up the conductor
     */
    @CallSuper
    override fun start() {
        //Don`t start if it is already started
        if (isStarted)
            return

        //Set isStarted to true
        isStarted = true
    }

    @CallSuper
    override fun end() {
        //Don`t end if it is already ended
        if (!isStarted)
            return

        //Set isStarted to false
        isStarted = false
    }

    @CallSuper
    override fun onStepInitiated(current: Any) {
        //Throw a Exception if the Step is not valid.
        if (!isStepValid(current))
            throw RuntimeException("The step isn't valid")
    }

    @CallSuper
    override fun onStepPaused(current: Any) {
        //Throw a Exception if the Step is not valid.
        if (!isStepValid(current))
            throw RuntimeException("The step isn't valid")
    }

    @CallSuper
    override fun onStepResult(current: Any, requestCode: Int, resultCode: Int) {
        //Throw a Exception if the Step is not valid.
        if (!isStepValid(current))
            throw RuntimeException("The step isn't valid")
    }

    @CallSuper
    override fun nextStep(current: Any, path: Path) {
        //Throw a Exception if the Step is not valid.
        if (!isStepValid(current))
            throw RuntimeException("The step isn't valid")
    }

    @CallSuper
    override fun previousStep(current: Any) {
        //Throw a Exception if the Step is not valid.
        if (!isStepValid(current))
            throw RuntimeException("The step isn't valid")
    }

    /**
     * Open method to check if the Step is valid
     * It validates if the step is a AppCompatActivity, Fragment, DialogFragment, or a View
     *
     * @param step The current step of the flow.
     */
    protected open fun isStepValid(step: Any): Boolean {
        //Check the type of the step
        return step is AppCompatActivity || step is Fragment || step is DialogFragment || step is View
    }
}