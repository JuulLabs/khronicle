package com.juul.khronicle

import kotlin.reflect.KClass

@KhronicleInternal
public class Metadata : ReadMetadata, WriteMetadata {

    private val storedData = mutableMapOf<Key<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    override operator fun <T : Any> get(key: Key<T>): T? =
        storedData[key] as? T

    @Suppress("UNCHECKED_CAST")
    override fun <K : Key<T>, T : Any> getAll(clazz: KClass<out K>): Map<K, T> =
        storedData.filter { (key, _) -> clazz.isInstance(key) } as Map<K, T>

    override operator fun <T : Any> set(key: Key<T>, value: T) {
        storedData[key] = value
    }

    override fun copy(): Metadata = Metadata().also { copy ->
        copy.storedData += this.storedData
    }

    internal fun clear() {
        storedData.clear()
    }

    override fun equals(other: Any?): Boolean = other is Metadata && storedData == other.storedData

    override fun hashCode(): Int = storedData.hashCode()

    override fun toString(): String = storedData.toString()
}

/**
 * Additional data associated with a log. It's important that [Logger] instances do NOT hold onto references
 * to [ReadMetadata] arguments after the function returns. If a [ReadMetadata] reference must be kept after
 * function return, create a [copy].
 */
public sealed interface ReadMetadata {

    public operator fun <T : Any> get(key: Key<T>): T?

    public fun <K : Key<T>, T : Any> getAll(clazz: KClass<out K>): Map<K, T>

    public fun copy(): ReadMetadata
}

/**
 * Additional data associated with a log. It's important that [Log] calls do NOT hold onto references
 * to [WriteMetadata] arguments after the lambda returns.
 */
public sealed interface WriteMetadata {
    public operator fun <T : Any> set(key: Key<T>, value: T)
}
