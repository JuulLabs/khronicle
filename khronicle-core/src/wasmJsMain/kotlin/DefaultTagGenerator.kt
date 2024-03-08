package com.juul.khronicle

internal actual val defaultTagGenerator: TagGenerator =
    ConstantTagGenerator("Unknown")
