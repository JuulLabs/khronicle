package com.juul.khronicle

@Deprecated(
    message = """
        The functionality of AndroidLogger has moved to ConsoleLogger.
        com.juul.khronicle:khronicle-android is deprecated; please use 
        com.juul.khronicle:khronicle-core instead. 
    """,
    replaceWith = ReplaceWith(
        expression = "ConsoleLogger",
        imports = ["com.juul.khronicle"],
    ),
)
public typealias AndroidLogger = ConsoleLogger
