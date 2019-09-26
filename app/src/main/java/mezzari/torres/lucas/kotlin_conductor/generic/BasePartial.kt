package mezzari.torres.lucas.kotlin_conductor.generic

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.AttributeSet
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.path.DefaultPath
import mezzari.torres.lucas.conductor.source.path.Path
import quevedo.soares.leandro.PartialView

/**
 * @author Lucas T. Mezzari
 * @since 17/09/2019
 */
abstract class BasePartial: PartialView {

    abstract val conductor: Conductor?

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onCreate() {
        super.onCreate()
        onInitiatingStep()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    open fun next(path: Int, conductor: Conductor? = this.conductor) {
        conductor?.nextStep(this, path)
    }
}