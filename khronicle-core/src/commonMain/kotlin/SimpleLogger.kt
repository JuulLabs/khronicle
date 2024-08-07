package com.juul.khronicle

/**
 * Simplified [Logger] which passes all logs through a single [log] function that accepts an
 * additional [LogLevel] argument. This is particularly useful when a logging backend does not have
 * separate function calls for each log level.
 */
public abstract class SimpleLogger : Logger {

    final override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log(LogLevel.Verbose, tag, message, metadata, throwable)
    }

    final override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log(LogLevel.Debug, tag, message, metadata, throwable)
    }

    final override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log(LogLevel.Info, tag, message, metadata, throwable)
    }

    final override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log(LogLevel.Warn, tag, message, metadata, throwable)
    }

    final override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log(LogLevel.Error, tag, message, metadata, throwable)
    }

    final override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log(LogLevel.Assert, tag, message, metadata, throwable)
    }

    public abstract fun log(level: LogLevel, tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)
}
