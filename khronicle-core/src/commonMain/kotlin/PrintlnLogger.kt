package com.juul.khronicle

/** Alternative to [ConsoleLogger] that does not take advantage of extra available features. */
public object PrintlnLogger : Logger {

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print('V', tag, message, throwable)
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print('D', tag, message, throwable)
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print('I', tag, message, throwable)
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print('W', tag, message, throwable)
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print('E', tag, message, throwable)
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print('A', tag, message, throwable)
    }

    private fun print(
        level: Char,
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        when (throwable) {
            null -> println("[$level/$tag] $message")
            else -> println("[$level/$tag] $message: ${throwable.stackTraceToString()}")
        }
    }
}
