package com.juul.khronicle

/** Classes which implement [Logger] can write logs. */
public interface Logger : HideFromStackTraceTag {

    /** Minimum level for this logger. Defaults to [LogLevel.Verbose] (all logs) if not overwritten. */
    public val minimumLogLevel: LogLevel
        get() = LogLevel.Verbose

    /** Log at verbose-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at debug-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at info-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at warn-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at error-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at assert-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)
}

public fun Logger.dynamic(level: LogLevel, tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
    when (level) {
        LogLevel.Verbose -> verbose(tag, message, metadata, throwable)
        LogLevel.Debug -> debug(tag, message, metadata, throwable)
        LogLevel.Info -> info(tag, message, metadata, throwable)
        LogLevel.Warn -> warn(tag, message, metadata, throwable)
        LogLevel.Error -> error(tag, message, metadata, throwable)
        LogLevel.Assert -> assert(tag, message, metadata, throwable)
    }
}
