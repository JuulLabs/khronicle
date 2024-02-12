package com.juul.khronicle

internal actual val defaultTagGenerator: TagGenerator = StackTraceTagGenerator

internal object StackTraceTagGenerator : TagGenerator, HideFromStackTraceTag {

    private val ignoreSubclassesOf = HideFromStackTraceTag::class.java

    override fun getTag() =
        Throwable().stackTrace.asSequence()
            .map { Class.forName(it.className) }
            .first { !ignoreSubclassesOf.isAssignableFrom(it) }
            .name
            .substringAfterLast('.')
            .substringBefore("$")
}
