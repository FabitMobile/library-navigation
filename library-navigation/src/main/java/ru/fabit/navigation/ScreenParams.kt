package ru.fabit.navigation

import android.os.Bundle
import java.io.Serializable

abstract class ScreenParams(private vararg val arguments: Any?) {
    open fun toArgumentsBundle(): Bundle {
        val constructor = this::class.constructors.firstOrNull() ?: return Bundle()
        val parameters = constructor.parameters.mapNotNull { parameter ->
            parameter.name
        }.zip(arguments).toMap()
        return Bundle().apply {
            parameters.forEach { (key, value) ->
                this[key] = value
            }
        }
    }

    inline operator fun <reified T> Bundle.set(key: String, value: T?) = when (value) {
        is Int -> putInt(key, value)
        is Byte -> putByte(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is String -> putString(key, value)
        is Boolean -> putBoolean(key, value)
        is IntArray -> putIntArray(key, value)
        is ByteArray -> putByteArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is Serializable -> putSerializable(key, value)
        else -> putString(key, null)
    }

    companion object
}