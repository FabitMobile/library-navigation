package ru.fabit.navigation

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.jvmErasure

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
        getValueClass(parameter) ?: get(parameter.name)
    }
    return constructor.call(*parameters.toTypedArray())
}

fun Bundle.getValueClass(parameter: KParameter): Any? {
    val clazz = parameter.type.jvmErasure
    return if (clazz.isValue) {
        val constructorValue = clazz.constructors.firstOrNull() ?: return null
        val value = get(parameter.name)
        constructorValue.call(value)
    } else
        null
}