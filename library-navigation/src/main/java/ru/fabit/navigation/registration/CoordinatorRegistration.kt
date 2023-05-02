package ru.fabit.navigation.registration

import ru.fabit.navigation.Action
import ru.fabit.navigation.Coordinator

class CoordinatorRegistration(
    private vararg val coordinators: Coordinator<Action>
) : Registrar {
    override fun register() {
        coordinators.forEach {
            it.register()
        }
    }
}