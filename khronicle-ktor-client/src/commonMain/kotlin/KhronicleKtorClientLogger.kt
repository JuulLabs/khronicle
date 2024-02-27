package com.juul.khronicle

import com.juul.khronicle.LogLevel.Verbose
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.Logger as KtorLogger

/**
 * A logger for use with a Ktor [HttpClient].
 *
 * ```kotlin
 * HttpClient {
 *     install(Logging) {
 *         logger = KhronicleKtorClientLogger()
 *     }
 * }
 * ```
 */
public class KhronicleKtorClientLogger(
    private val level: LogLevel = Verbose,
    private val writeMetadata: (WriteMetadata) -> Unit = {},
) : KtorLogger {

    override fun log(message: String) {
        Log.dynamic(level) { metadata ->
            writeMetadata.invoke(metadata)
            message
        }
    }
}
