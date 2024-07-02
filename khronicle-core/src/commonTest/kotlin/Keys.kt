package com.juul.khronicle

internal object AnyKey : Key<Any>

internal object StringKey : Key<String>

internal object BooleanKey : Key<Boolean>

internal data class DynamicKey(
    val value: String,
) : Key<String>
