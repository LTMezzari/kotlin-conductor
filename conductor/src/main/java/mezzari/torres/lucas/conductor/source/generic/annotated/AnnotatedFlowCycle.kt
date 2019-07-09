package mezzari.torres.lucas.conductor.source.generic.annotated

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Class of constants for the { @link AnnotatedConductor }
 * It`s constants should be passed in the { @link ConductorAnnotation }
 *
 * @see AnnotatedConductor
 * @see mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
 **/
object AnnotatedFlowCycle {
    const val NEXT: Int = 0
    const val PREVIOUS: Int = 1
    const val STEP_INITIATED: Int = 2
    const val STEP_PAUSED: Int = 3
    const val STEP_RESULT: Int = 4
}