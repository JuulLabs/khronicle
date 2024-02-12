package com.juul.khronicle

import java.util.function.Supplier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StackTraceTagGeneratorTests {

    @Test
    fun tagMatchesClassName() {
        val generatedTag = StackTraceTagGenerator.getTag()
        assertEquals(StackTraceTagGeneratorTests::class.java.simpleName, generatedTag)
    }

    @Test
    fun tagStripsAnonymousClassNumber() {
        val supplier = Supplier<String> { StackTraceTagGenerator.getTag() }

        /* Examples of `class.java.name`:
         *
         * | Kotlin | supplier::class.java.name                                                                     |
         * |--------|-----------------------------------------------------------------------------------------------|
         * | 1.4.31 | com.juul.khronicle.StackTraceTagGeneratorTests$tagStripsAnonymousClassNumber$supplier$1 |
         * | 1.5.10 | com.juul.khronicle.StackTraceTagGeneratorTests$$Lambda$4/1813123723                     |
         */

        // Double check that the way we've written this actually generates
        // an anonymous class, instead of being optimized by the compiler.
        assertTrue(supplier::class.java.name.contains("\$"))

        assertEquals("StackTraceTagGeneratorTests", supplier.get())
    }

    @Test
    fun tagIgnoreClassesMarkedWithHide() {
        class HiddenClass : HideFromStackTraceTag {
            fun getTag() = StackTraceTagGenerator.getTag()
        }
        assertEquals(StackTraceTagGeneratorTests::class.java.simpleName, HiddenClass().getTag())
    }
}
