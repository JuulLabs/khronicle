package com.juul.khronicle

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.juul.khronicle.test.buildMetadata
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

private const val TAG = "sample-tag"
private const val MESSAGE = "A longer sample message"

@RunWith(AndroidJUnit4::class)
class ConsoleLoggerTests {

    private lateinit var testOutBuffer: ByteArrayOutputStream
    private lateinit var testOut: PrintStream

    @BeforeTest
    fun setup() {
        testOutBuffer = ByteArrayOutputStream()
        testOut = PrintStream(testOutBuffer, true)
        ShadowLog.stream = testOut
    }

    @AfterTest
    fun teardown() {
        testOut.close()
    }

    @Test
    fun checkVerboseWithoutThrowable() {
        ConsoleLogger.verbose(TAG, MESSAGE, buildMetadata(), null)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkVerboseWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.verbose(TAG, MESSAGE, buildMetadata(), throwable)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkDebugWithoutThrowable() {
        ConsoleLogger.debug(TAG, MESSAGE, buildMetadata(), null)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkDebugWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.debug(TAG, MESSAGE, buildMetadata(), throwable)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkInfoWithoutThrowable() {
        ConsoleLogger.info(TAG, MESSAGE, buildMetadata(), null)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkInfoWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.info(TAG, MESSAGE, buildMetadata(), throwable)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkWarnWithoutThrowable() {
        ConsoleLogger.warn(TAG, MESSAGE, buildMetadata(), null)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkWarnWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.warn(TAG, MESSAGE, buildMetadata(), throwable)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkErrorWithoutThrowable() {
        ConsoleLogger.error(TAG, MESSAGE, buildMetadata(), null)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkErrorWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.error(TAG, MESSAGE, buildMetadata(), throwable)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkAssertWithoutThrowable() {
        ConsoleLogger.assert(TAG, MESSAGE, buildMetadata(), null)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkAssertWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.assert(TAG, MESSAGE, buildMetadata(), throwable)
        val logString = testOutBuffer.toString()
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }
}
