package mezzari.torres.lucas.conductor.source.generic.implementation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.path.DefaultPath
import mezzari.torres.lucas.conductor.source.path.Path

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 **/
abstract class BaseFragment: Fragment() {

    abstract val conductor: Conductor?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitiatingStep()
    }

    override fun onPause() {
        super.onPause()
        onPausingStep()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onStepResult(requestCode, resultCode)
    }

    open fun onInitiatingStep() {
        conductor?.onStepInitiated(this)
    }

    open fun onPausingStep() {
        conductor?.onStepPaused(this)
    }

    open fun onStepResult(requestCode: Int, resultCode: Int) {
        conductor?.onStepResult(this, requestCode, resultCode)
    }

    open fun previous(conductor: Conductor? = this.conductor) {
        conductor?.previousStep(this)
    }

    open fun next(path: Path = DefaultPath.MAIN, conductor: Conductor? = this.conductor) {
        conductor?.nextStep(this, path)
    }
}