package com.juul.khronicle

import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock

public fun Logger.synchronized(): Logger = SynchronizedLogger(this)

private class SynchronizedLogger(
    private val inner: Logger,
) : Logger {

    private val guard = reentrantLock()

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        guard.withLock { inner.verbose(tag, message, metadata, throwable) }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        guard.withLock { inner.debug(tag, message, metadata, throwable) }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        guard.withLock { inner.info(tag, message, metadata, throwable) }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        guard.withLock { inner.warn(tag, message, metadata, throwable) }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        guard.withLock { inner.error(tag, message, metadata, throwable) }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        guard.withLock { inner.assert(tag, message, metadata, throwable) }
    }
}
