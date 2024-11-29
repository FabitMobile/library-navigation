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

    private operator fun Bundle.set(key: String, value: Any?) {
        val successfullyPutPrimitive = putPrimitive(key, value)

        if (!successfullyPutPrimitive) {
            if (value != null && value::class.isValue) {
                val clazz = value::class
                val valueName = clazz.constructors.firstOrNull()?.parameters?.getOrNull(0)?.name
                val f = clazz.java.declaredFields.firstOrNull { it.name == valueName } ?: return
                f.isAccessible = true
                set(key, f.get(value))
            }
        }
    }

    private fun Bundle.putPrimitive(key: String, value: Any?): Boolean {
        when (value) {
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

            else -> {
                return false
            }
        }
        return true
    }

    companion object
}