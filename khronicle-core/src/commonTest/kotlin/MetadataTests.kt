package com.juul.khronicle

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class MetadataTests {

    @Test
    fun copy_isShallow() {
        val expected = Any()
        val metadata = Metadata().also {
            it[AnyKey] = expected
        }
        val actual = metadata.copy()[AnyKey]
        assertSame(expected, actual)
    }

    @Test
    fun get_respectsKeys() {
        val metadata = Metadata().also {
            it[StringKey] = "test"
            it[BooleanKey] = false
        }
        assertEquals("test", metadata[StringKey])
        assertEquals(false, metadata[BooleanKey])
    }

    @Test
    fun get_withNoSet_returnsNull() {
        val metadata = Metadata()
        assertNull(metadata[StringKey])
    }

    @Test
    fun get_afterReset_returnsNull() {
        val metadata = Metadata().also {
            it[StringKey] = "test"
        }
        metadata.clear()
        assertNull(metadata[StringKey])
    }


    @Test
    fun get_withDynamicKeys_respectsArguments() {
        val metadata = Metadata().also { metadata ->
            metadata[DynamicKey("foo")] = "first"
            metadata[DynamicKey("bar")] = "second"
        }
        assertEquals(expected = "first", actual = metadata[DynamicKey("foo")])
        assertEquals(expected = "second", actual = metadata[DynamicKey("bar")])
    }

    @Test
    fun getAll_withDynamicKeys_returnsFullMap() {
        val metadata = Metadata().also { metadata ->
            metadata[DynamicKey("foo")] = "first"
            metadata[DynamicKey("bar")] = "second"
        }
        assertEquals(
            expected = mapOf(
                DynamicKey("foo") to "first",
                DynamicKey("bar") to "second",
            ),
            actual = metadata.getAll(DynamicKey::class),
        )
    }
}
