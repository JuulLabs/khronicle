package com.juul.khronicle

import kotlin.RequiresOptIn.Level.ERROR
import kotlin.annotation.AnnotationRetention.BINARY

/**
 * Marks a class as internal to Khronicle. These APIs are left `public` so they may be used by other
 * modules, but should not be used outside (by consumers) of Khronicle.
 */
@MustBeDocumented
@Retention(value = BINARY)
@RequiresOptIn(level = ERROR)
public annotation class KhronicleInternal
