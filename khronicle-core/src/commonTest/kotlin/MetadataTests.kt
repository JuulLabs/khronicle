package com.juul.khronicle

import com.juul.khronicle.test.buildMetadata
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class MetadataTests {

    @Test
    fun copy_isShallow() {
        val expected = Any()
        val metadata = buildMetadata {
            it[AnyKey] = expected
        }
        val actual = metadata.copy()[AnyKey]
        assertSame(expected, actual)
    }

    @Test
    fun get_respectsKeys() {
        val metadata = buildMetadata {
            it[StringKey] = "test"
            it[BooleanKey] = false
        }
        assertEquals("test", metadata[StringKey])
        assertEquals(false, metadata[BooleanKey])
    }

    @Test
    fun get_withNoSet_returnsNull() {
        val metadata = buildMetadata()
        assertNull(metadata[StringKey])
    }

    @Test
    fun get_afterReset_returnsNull() {
        val metadata = buildMetadata {
            it[StringKey] = "test"
        }
        (metadata as Metadata).clear()
        assertNull(metadata[StringKey])
    }

    @Test
    fun get_withDynamicKeys_respectsArguments() {
        val metadata = buildMetadata { metadata ->
            metadata[DynamicKey("foo")] = "first"
            metadata[DynamicKey("bar")] = "second"
        }
        assertEquals(expected = "first", actual = metadata[DynamicKey("foo")])
        assertEquals(expected = "second", actual = metadata[DynamicKey("bar")])
    }

    @Test
    fun getAll_withDynamicKeys_returnsFullMap() {
        val metadata = buildMetadata { metadata ->
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
