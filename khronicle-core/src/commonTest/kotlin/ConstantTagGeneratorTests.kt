package com.juul.khronicle

import kotlin.test.Test
import kotlin.test.assertEquals

class ConstantTagGeneratorTests {

    @Test
    fun constantTagGeneratorWorks() {
        val expected = "Constant"
        val generator = ConstantTagGenerator(expected)
        assertEquals(expected, generator.getTag())
    }
}
