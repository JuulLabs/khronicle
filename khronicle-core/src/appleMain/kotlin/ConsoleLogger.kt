package com.juul.khronicle

import com.juul.khronicle.ConsoleLogger.assert
import com.juul.khronicle.ConsoleLogger.debug
import com.juul.khronicle.ConsoleLogger.error
import com.juul.khronicle.ConsoleLogger.info
import com.juul.khronicle.ConsoleLogger.verbose
import com.juul.khronicle.ConsoleLogger.warn
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.FILE
import platform.posix.fflush
import platform.posix.fprintf
import platform.posix.stderr
import platform.posix.stdout

/**
 * Logger for standard-out and standard-error.
 *
 * - Calls to [verbose], [debug], [info], and [warn] map to standard-out.
 * - Calls to [error] and [assert] map to standard-error.
 */
@OptIn(ExperimentalForeignApi::class)
public actual object ConsoleLogger : Logger {

    private fun print(stream: CPointer<FILE>?, severity: String, tag: String, message: String, throwable: Throwable?) {
        val formattedString = if (throwable == null) {
            "[$severity/$tag] $message\n"
        } else {
            "[$severity/$tag] $message: ${throwable.stackTraceToString()}\n"
        }

        fprintf(stream, formattedString)
        fflush(stream)
    }

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "V", tag, message, throwable)
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "D", tag, message, throwable)
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "I", tag, message, throwable)
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "W", tag, message, throwable)
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stderr, "E", tag, message, throwable)
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stderr, "A", tag, message, throwable)
    }
}
