package ru.fabit.navigation

interface Coordinator<out T : Action> {
    fun register()

    fun receive(action: Action) {
        throw Exception("I can't receive action")
    }
}