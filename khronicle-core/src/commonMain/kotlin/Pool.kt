package com.juul.khronicle

import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock

internal class Pool<T>(
    private val factory: () -> T,
    private val refurbish: (T) -> Unit,
) {
    private val lock = reentrantLock()
    private val cache = ArrayDeque<T>()

    fun borrow(): T = lock.withLock { cache.removeLastOrNull() } ?: factory()

    fun recycle(value: T) {
        refurbish(value)
        lock.withLock { cache.addLast(value) }
    }
}
