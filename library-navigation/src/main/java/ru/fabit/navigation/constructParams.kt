package ru.fabit.navigation

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle

inline fun <reified Params : ScreenParams> SavedStateHandle.constructParams(): Params {
    val constructor = Params::class.constructors.firstOrNull()
        ?: return ScreenParams as Params
    val parameters = constructor.parameters.map { parameter ->
        parameter.name?.let {
            get(it) as Any?
        }
    }
    return constructor.call(*parameters.toTypedArray())
}

inline fun <reified Params : ScreenParams> Bundle.constructParams(): Params {
    val constructor = Params::class.constructors.firstOrNull()
        ?: return ScreenParams as Params
    val parameters = constructor.parameters.map { parameter ->
        parameter.name?.let {
            get(it)
        }
    }
    return constructor.call(*parameters.toTypedArray())
}