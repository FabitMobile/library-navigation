package ru.fabit.navigation

abstract class Mediator<ParentAction : Action, ChildAction : Action> {

    private var parentCoordinator: Coordinator<ParentAction>? = null
    private var childCoordinator: Coordinator<ChildAction>? = null

    fun registerCoordinators(
        parentCoordinator: Coordinator<ParentAction>,
        childCoordinator: Coordinator<ChildAction>
    ) {
        this.parentCoordinator = parentCoordinator
        this.childCoordinator = childCoordinator
    }

    fun registerAsParentCoordinator(
        parentCoordinator: Coordinator<ParentAction>
    ) {
        this.parentCoordinator = parentCoordinator
    }

    fun registerAsChildCoordinator(
        childCoordinator: Coordinator<ChildAction>
    ) {
        this.childCoordinator = childCoordinator
    }

    fun sendToParent(action: ParentAction) {
        parentCoordinator?.receive(action)
            ?: throw Exception("You need call registerCoordinators method before")
    }

    fun sendToChild(action: ChildAction) {
        childCoordinator?.receive(action)
            ?: throw Exception("You need call registerCoordinators method before")
    }
}