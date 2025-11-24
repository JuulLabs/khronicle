package com.juul.khronicle

import com.juul.khronicle.Log.dispatcher

private val metadataPool = Pool(factory = ::Metadata, refurbish = Metadata::clear)

/** Global logging object. To receive logs, call [dispatcher].[install][DispatchLogger.install]. */
public object Log {

    /** Global log dispatcher. */
    public val dispatcher: DispatchLogger = DispatchLogger()

    /** Send a log message at a dynamic [level] to the global dispatcher. */
    public fun dynamic(level: LogLevel, tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        when (level) {
            LogLevel.Verbose -> verbose(tag, throwable, message)
            LogLevel.Debug -> debug(tag, throwable, message)
            LogLevel.Info -> info(tag, throwable, message)
            LogLevel.Warn -> warn(tag, throwable, message)
            LogLevel.Error -> error(tag, throwable, message)
            LogLevel.Assert -> assert(tag, throwable, message)
        }
    }

    /** Send a verbose-level log message to the global dispatcher. */
    public fun verbose(tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Verbose)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.verbose(tag, body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send a debug-level log message to the global dispatcher. */
    public fun debug(tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Debug)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.debug(tag, body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an info-level log message to the global dispatcher. */
    public fun info(tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Info)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.info(tag, body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an warn-level log message to the global dispatcher. */
    public fun warn(tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Warn)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.warn(tag, body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an error-level log message to the global dispatcher. */
    public fun error(tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Error)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.error(tag, body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an assert-level log message to the global dispatcher. */
    public fun assert(tag: String, throwable: Throwable? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Assert)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.assert(tag, body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    private fun DispatchLogger.canLog(level: LogLevel): Boolean =
        hasConsumers && level >= minimumLogLevel
}
