package mezzari.torres.lucas.conductor.annotation

import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Annotation for The { @link AnnotatedConductor }
 * It is used to mark the functions that the conductor will call in its cycle and step
 *
 * @param step The class of the Step
 * @param managerCycle A int constant from { @link AnnotatedFlowCycle }
 *
 * @see mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
 * @see mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
 **/
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConductorAnnotation (
    val step: KClass<*>,
    val managerCycle: Int
)