package mezzari.torres.lucas.conductor.annotation

import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * Annotation for the { @link ModulatedConductor }
 * It is used to link the { @link Module } to it`s step
 *
 * @param step The class of the step
 *
 * @see mezzari.torres.lucas.conductor.source.generic.modulated.ModulatedConductor
 * @see mezzari.torres.lucas.conductor.source.generic.modulated.ConductorModule
 **/
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Module (
    val step: KClass<*>
)