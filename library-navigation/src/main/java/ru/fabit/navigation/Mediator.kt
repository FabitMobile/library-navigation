package ru.fabit.navigation

abstract class Mediator<ParentCoordinator : Coordinator<ParentAction>, ChildCoordinator : Coordinator<ChildAction>, ParentAction : Action, ChildAction : Action> {

    private var parentCoordinator: ParentCoordinator? = null
    private var childCoordinator: ChildCoordinator? = null

    fun registerCoordinators(
        parentCoordinator: ParentCoordinator,
        childCoordinator: ChildCoordinator
    ) {
        this.parentCoordinator = parentCoordinator
        this.childCoordinator = childCoordinator
    }

    fun registerAsParentCoordinator(
        parentCoordinator: ParentCoordinator
    ) {
        this.parentCoordinator = parentCoordinator
    }

    fun registerAsChildCoordinator(
        childCoordinator: ChildCoordinator
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