package com.juul.khronicle

import com.juul.khronicle.LogLevel.Assert
import com.juul.khronicle.LogLevel.Debug
import com.juul.khronicle.LogLevel.Error
import com.juul.khronicle.LogLevel.Info
import com.juul.khronicle.LogLevel.Verbose
import com.juul.khronicle.LogLevel.Warn
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update

private val EMPTY_STATE = DispatcherState(emptySet())

private data class DispatcherState(
    val consumers: Set<Logger>,
) {
    val logLevel: LogLevel =
        consumers.minOfOrNull { it.minimumLogLevel } ?: Assert
}

/** Implementation of [Logger] which dispatches calls to consumer [Logger]s. */
public class DispatchLogger : Logger {

    private val state = atomic(EMPTY_STATE)

    override val minimumLogLevel: LogLevel
        get() = state.value.logLevel

    /** `false` if no consumers have been installed, `true` if at least one consumer has been installed. */
    internal val hasConsumers: Boolean
        get() = state.value.consumers.isNotEmpty()

    /** Add a consumer to receive future dispatch calls. */
    public fun install(consumer: Logger) {
        state.update { DispatcherState(it.consumers + consumer) }
    }

    /** Uninstall all installed consumers. */
    public fun clear() {
        state.value = EMPTY_STATE
    }

    // This class looks like a great candidate for SimpleLogger, but we can avoid a few function
    // calls and conditionals by explicitly writing the different verbose/debug/etc functions.
    // Normally I try not to worry about optimization at this level, but it can really make a
    // difference if logging gets placed in a tight loop.

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Avoid asSequence, filter, etc to keep this as close to allocation free as possible.
        for (logger in state.value.consumers) {
            if (logger.minimumLogLevel <= Verbose) {
                logger.verbose(tag, message, metadata, throwable)
            }
        }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Avoid asSequence, filter, etc to keep this as close to allocation free as possible.
        for (logger in state.value.consumers) {
            if (logger.minimumLogLevel <= Debug) {
                logger.debug(tag, message, metadata, throwable)
            }
        }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Avoid asSequence, filter, etc to keep this as close to allocation free as possible.
        for (logger in state.value.consumers) {
            if (logger.minimumLogLevel <= Info) {
                logger.info(tag, message, metadata, throwable)
            }
        }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Avoid asSequence, filter, etc to keep this as close to allocation free as possible.
        for (logger in state.value.consumers) {
            if (logger.minimumLogLevel <= Warn) {
                logger.warn(tag, message, metadata, throwable)
            }
        }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Avoid asSequence, filter, etc to keep this as close to allocation free as possible.
        for (logger in state.value.consumers) {
            if (logger.minimumLogLevel <= Error) {
                logger.error(tag, message, metadata, throwable)
            }
        }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Avoid asSequence, filter, etc to keep this as close to allocation free as possible.
        for (logger in state.value.consumers) {
            if (logger.minimumLogLevel <= Assert) {
                logger.assert(tag, message, metadata, throwable)
            }
        }
    }
}
