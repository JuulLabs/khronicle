package com.juul.khronicle

import com.juul.khronicle.test.CallListLogger
import com.juul.khronicle.test.buildMetadata
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class FilterLoggerTests {

    @Test
    fun verbose_whenDenied_doesNotCallInner() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> false }
            .verbose("tag", "message", buildMetadata(), throwable)
        assertTrue(inner.verboseCalls.isEmpty())
    }

    @Test
    fun verbose_whenPermitted_callsInnerWithSameArguments() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> true }
            .verbose("tag", "message", buildMetadata(), throwable)
        val call = inner.verboseCalls.single()
        assertEquals("tag", call.tag)
        assertEquals("message", call.message)
        assertEquals(buildMetadata(), call.metadata)
        assertSame(throwable, call.throwable)
    }

    @Test
    fun debug_whenDenied_doesNotCallInner() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> false }
            .debug("tag", "message", buildMetadata(), throwable)
        assertTrue(inner.debugCalls.isEmpty())
    }

    @Test
    fun debug_whenPermitted_callsInnerWithSameArguments() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> true }
            .debug("tag", "message", buildMetadata(), throwable)
        val call = inner.debugCalls.single()
        assertEquals("tag", call.tag)
        assertEquals("message", call.message)
        assertEquals(buildMetadata(), call.metadata)
        assertSame(throwable, call.throwable)
    }

    @Test
    fun info_whenDenied_doesNotCallInner() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> false }
            .info("tag", "message", buildMetadata(), throwable)
        assertTrue(inner.infoCalls.isEmpty())
    }

    @Test
    fun info_whenPermitted_callsInnerWithSameArguments() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> true }
            .info("tag", "message", buildMetadata(), throwable)
        val call = inner.infoCalls.single()
        assertEquals("tag", call.tag)
        assertEquals("message", call.message)
        assertEquals(buildMetadata(), call.metadata)
        assertSame(throwable, call.throwable)
    }

    @Test
    fun warn_whenDenied_doesNotCallInner() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> false }
            .warn("tag", "message", buildMetadata(), throwable)
        assertTrue(inner.warnCalls.isEmpty())
    }

    @Test
    fun warn_whenPermitted_callsInnerWithSameArguments() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> true }
            .warn("tag", "message", buildMetadata(), throwable)
        val call = inner.warnCalls.single()
        assertEquals("tag", call.tag)
        assertEquals("message", call.message)
        assertEquals(buildMetadata(), call.metadata)
        assertSame(throwable, call.throwable)
    }

    @Test
    fun error_whenDenied_doesNotCallInner() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> false }
            .error("tag", "message", buildMetadata(), throwable)
        assertTrue(inner.errorCalls.isEmpty())
    }

    @Test
    fun error_whenPermitted_callsInnerWithSameArguments() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> true }
            .error("tag", "message", buildMetadata(), throwable)
        val call = inner.errorCalls.single()
        assertEquals("tag", call.tag)
        assertEquals("message", call.message)
        assertEquals(buildMetadata(), call.metadata)
        assertSame(throwable, call.throwable)
    }

    @Test
    fun assert_whenDenied_doesNotCallInner() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> false }
            .assert("tag", "message", buildMetadata(), throwable)
        assertTrue(inner.assertCalls.isEmpty())
    }

    @Test
    fun assert_whenPermitted_callsInnerWithSameArguments() {
        val inner = CallListLogger()
        val throwable = Throwable()
        inner
            .withFilter { _, _, _, _ -> true }
            .assert("tag", "message", buildMetadata(), throwable)
        val call = inner.assertCalls.single()
        assertEquals("tag", call.tag)
        assertEquals("message", call.message)
        assertEquals(buildMetadata(), call.metadata)
        assertSame(throwable, call.throwable)
    }
}
