package com.juul.khronicle.test

import com.juul.khronicle.Metadata
import com.juul.khronicle.ReadMetadata
import com.juul.khronicle.WriteMetadata

public fun buildMetadata(
    block: (metadata: WriteMetadata) -> Unit = {},
): ReadMetadata = Metadata().apply(block)
