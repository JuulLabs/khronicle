package com.juul.khronicle

import android.util.Log

/** [Logger] backed by Android's [Log], for output to Logcat. */
public actual object ConsoleLogger : Logger {

    actual override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    actual override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    actual override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    actual override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    actual override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    actual override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.wtf(tag, message, throwable)
    }
}
