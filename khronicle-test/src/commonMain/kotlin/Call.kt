package com.juul.khronicle.test

import com.juul.khronicle.LogLevel
import com.juul.khronicle.ReadMetadata

/** The arguments passed to [CallListLogger.log]. */
public data class Call(
    val level: LogLevel,
    val tag: String,
    val message: String,
    val throwable: Throwable?,
    val metadata: ReadMetadata,
)
