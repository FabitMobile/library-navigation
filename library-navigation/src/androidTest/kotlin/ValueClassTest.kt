package ru.fabit.navigation

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.Serializable

@RunWith(AndroidJUnit4::class)
class ValueClassTest {
    @Test
    fun test() {
        val expected = Params(10, DA("dada"), DA2(321), Serial(true), listOf(DA("list")))
        val b = expected.toArgumentsBundle()
        val actual = b.constructParams<Params>()
        assertEquals(expected, actual)
    }

    @JvmInline
    value class DA(val value: String)

    @JvmInline
    value class DA2(val integer: Int)

    data class Serial(val valid: Boolean) : Serializable

    data class Params(
        val i: Int,
        val test: DA,
        val test2: DA2,
        val serial: Serial,
        val list: List<DA>
    ) : ScreenParams(i, test, test2, serial, list)
}