package com.juul.khronicle

/** Constant tag generator, for when performance is more important than useful implicit tags. */
public class ConstantTagGenerator(
    private val tag: String,
) : TagGenerator {
    override fun getTag(): String = tag
}
