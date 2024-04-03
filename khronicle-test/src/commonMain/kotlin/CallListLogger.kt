package com.juul.khronicle.test

import com.juul.khronicle.LogFilter
import com.juul.khronicle.LogLevel
import com.juul.khronicle.Logger
import com.juul.khronicle.ReadMetadata
import com.juul.khronicle.SimpleLogger
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.getAndUpdate

/**
 * [Logger] implementation which keeps every log in-memory and makes them available for retrieval as
 * a [List] of [Call]s.
 *
 * This is useful for testing functionality that affects which logs propagate to a [Logger], such as
 * a custom [LogFilter].
 */
public open class CallListLogger : SimpleLogger() {

    private val atomicAllCalls = atomic(emptyList<Call>())
    public val allCalls: List<Call> get() = atomicAllCalls.value
    public val verboseCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Verbose }
    public val debugCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Debug }
    public val infoCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Info }
    public val warnCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Warn }
    public val errorCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Error }
    public val assertCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Assert }

    override fun log(level: LogLevel, tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(level, tag = tag, message = message, throwable, metadata.copy()) }
    }
}
