# library-navigation

Содержит базовые классы для реализации навигации через паттерн Coordinator и Mediator

Action - всевозможные действия передаваемые между координаторами

```kotlin
sealed interface Feature1Action : Action
```

```kotlin
sealed interface Feature2Action : Action {
    object OpenFeature2Screen : Feature2Action
}
```

Coordinator осуществляет саму навигацию путем передачи Action через Mediator, при этом сам
подписывается на свои экшены

```kotlin
class Feature1Coordinator(
    private val feature1AndFeature2Mediator: Feature1AndFeature2Mediator
) : Coordinator<Feature1Action> {
    override fun receive(action: Action) {}

    override fun register() {
        feature1AndFeature2Mediator.registerAsParentCoordinator(this)
    }

    fun openFeature2() {
        feature1AndFeature2Mediator.sendToChild(Feature2Action.OpenFeature2Screen)
    }
}
```

```kotlin
class Feature2Coordinator(
    private val feature1AndFeature2Mediator: Feature1AndFeature2Mediator
) : Coordinator<Feature2Action> {
    override fun receive(action: Action) {
        when (action) {
            is Feature2Action.OpenFeature2Screen -> openFeature2Screen()
        }
    }

    override fun register() {
        feature1AndFeature2Mediator.registerAsChildCoordinator(this)
    }

    fun openFeature2Screen() {
        ...
    }
}
```

Mediator образует связь между двумя координаторами

```kotlin
class Feature1AndFeature2Mediator :
    Mediator<Feature1Coordinator, Feature2Coordinator, Feature1Action, Feature2Action>()
```

Подписка на события для координаторов реализуется классами `CoordinatorRegistration`
и `InternalCoordinatorRegistration`

Передача аргументов при навигации осуществляется функциями `newInstance` и `constructParams`, для
отправки и получаения соответственно

```kotlin
data class FeatureScreen(
    val id: Int
) : ScreenParams(id) {
    fun createFragment(): Fragment {
        return FeatureFragment().newInstance(this)
    }
}
```

```kotlin
val params = savedStateHandle.constructParams<FeatureScreen>()
```