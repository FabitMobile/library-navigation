package ru.fabit.navigation.registration

import ru.fabit.navigation.Action
import ru.fabit.navigation.Coordinator
import ru.fabit.navigation.Mediator

class InternalCoordinatorRegistration(
    private vararg val mediatorAndCoordinator: Pair<Mediator<Coordinator<Action>, Coordinator<Action>, Action, Action>, Coordinator<Action>>,
) : Registrar {
    override fun register() {
        mediatorAndCoordinator.forEach { (mediator, childCoordinator) ->
            mediator.registerAsChildCoordinator(childCoordinator)
        }
    }
}