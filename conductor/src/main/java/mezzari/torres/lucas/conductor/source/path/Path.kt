package mezzari.torres.lucas.conductor.source.path

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 *
 * A interface to be implemented in Enums to signalize them as possible Paths to the { @link Conductor }
 *
 * @see mezzari.torres.lucas.conductor.source.Conductor
 **/
interface Path {
    companion object {
        const val MAIN = -1
    }
}