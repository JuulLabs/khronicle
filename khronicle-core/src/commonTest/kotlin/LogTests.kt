package com.juul.khronicle

import com.juul.khronicle.test.CallListLogger
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

private const val TAG = "LogTests"

class LogTests {

    @AfterTest
    fun cleanup() {
        Log.dispatcher.clear()
    }

    @Test
    fun verboseWithNoConsumerDoesntCreateMessage() {
        Log.verbose(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun debugWithNoConsumerDoesntCreateMessage() {
        Log.debug(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun infoWithNoConsumerDoesntCreateMessage() {
        Log.info(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun warnWithNoConsumerDoesntCreateMessage() {
        Log.warn(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun errorWithNoConsumerDoesntCreateMessage() {
        Log.error(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun assertWithNoConsumerDoesntCreateMessage() {
        Log.assert(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun verboseWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.verbose(TAG) { "message" }
        assertTrue(logger.verboseCalls.isNotEmpty())
    }

    @Test
    fun debugWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.debug(TAG) { "message" }
        assertTrue(logger.debugCalls.isNotEmpty())
    }

    @Test
    fun infoWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.info(TAG) { "message" }
        assertTrue(logger.infoCalls.isNotEmpty())
    }

    @Test
    fun warnWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.warn(TAG) { "message" }
        assertTrue(logger.warnCalls.isNotEmpty())
    }

    @Test
    fun errorWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.error(TAG) { "message" }
        assertTrue(logger.errorCalls.isNotEmpty())
    }

    @Test
    fun assertWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.assert(TAG) { "message" }
        assertTrue(logger.assertCalls.isNotEmpty())
    }

    @Test
    fun verbose_withAssertOnlyLogger_doesntCreateMessage() {
        val logger = CallListLogger().withMinimumLogLevel(LogLevel.Assert)
        Log.dispatcher.install(logger)
        Log.verbose(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun debug_withAssertOnlyLogger_doesntCreateMessage() {
        val logger = CallListLogger().withMinimumLogLevel(LogLevel.Assert)
        Log.dispatcher.install(logger)
        Log.debug(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun info_withAssertOnlyLogger_doesntCreateMessage() {
        val logger = CallListLogger().withMinimumLogLevel(LogLevel.Assert)
        Log.dispatcher.install(logger)
        Log.info(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun warn_withAssertOnlyLogger_doesntCreateMessage() {
        val logger = CallListLogger().withMinimumLogLevel(LogLevel.Assert)
        Log.dispatcher.install(logger)
        Log.warn(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun error_withAssertOnlyLogger_doesntCreateMessage() {
        val logger = CallListLogger().withMinimumLogLevel(LogLevel.Assert)
        Log.dispatcher.install(logger)
        Log.error(TAG) { fail("Lambda should not be called") }
    }

    @Test
    fun assert_withAssertOnlyLogger_createsMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger.withMinimumLogLevel(LogLevel.Assert))
        Log.assert(TAG) { "Success" }
        assertEquals(1, logger.assertCalls.size)
    }

    @Test
    fun verboseExplicitTagIsUsed() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.verbose(TAG) { "Test message" }
        assertTrue { logger.verboseCalls.all { it.tag == TAG } }
    }

    @Test
    fun debugExplicitTagIsUsed() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.debug(TAG) { "Test message" }
        assertTrue { logger.debugCalls.all { it.tag == TAG } }
    }

    @Test
    fun infoExplicitTagIsUsed() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.info(TAG) { "Test message" }
        assertTrue { logger.infoCalls.all { it.tag == TAG } }
    }

    @Test
    fun warnExplicitTagIsUsed() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.warn(TAG) { "Test message" }
        assertTrue { logger.warnCalls.all { it.tag == TAG } }
    }

    @Test
    fun errorExplicitTagIsUsed() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.error(TAG) { "Test message" }
        assertTrue { logger.errorCalls.all { it.tag == TAG } }
    }

    @Test
    fun assertExplicitTagIsUsed() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.assert(TAG) { "Test message" }
        assertTrue { logger.assertCalls.all { it.tag == TAG } }
    }

    @Test
    fun verbose_withMetadata_canReadInLogger() {
        val logger = object : CallListLogger() {
            override fun log(level: LogLevel, tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
                super.log(level, tag, message, metadata, throwable)
                assertEquals("test", metadata[StringKey])
            }
        }
        Log.dispatcher.install(logger)
        Log.verbose(TAG) { metadata ->
            metadata[StringKey] = "test"
            "Test message"
        }
        // When doing the pattern of asserting inside of a callback, it's easy to accidentally not call the callback.
        // This line verifies that `logger.log` is actually called, and therefore the test actually contained an assert.
        assertEquals(1, logger.verboseCalls.size)
    }

    @Test
    fun dynamic_atEachLevel_usesAppropriateLevel() {
        for (level in LogLevel.entries) {
            val logger = CallListLogger()
            Log.dispatcher.install(logger)
            Log.dynamic(level, TAG) { "test" }
            assertTrue { logger.allCalls.any { it.level == level } }
            Log.dispatcher.clear()
        }
    }
}
