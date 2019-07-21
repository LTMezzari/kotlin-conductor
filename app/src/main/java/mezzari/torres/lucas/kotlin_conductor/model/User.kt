package mezzari.torres.lucas.kotlin_conductor.model

/**
 * @author Lucas T. Mezzari
 * @since 09/07/2019
 **/
class User (
    val username: String = "",
    val password: String = "",
    val shouldRememberPassword: Boolean = false
)