package com.juul.khronicle

/** Logger for the console, taking advantage of available features such as stderr or js console levels. */
public expect object ConsoleLogger : Logger {

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)
}
