package com.juul.khronicle

import com.juul.khronicle.test.CallListLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

private const val URL = "https://localhost"
private const val RESPONSE = "Hello, world."

class KhronicleKtorClientLoggerTests {

    @AfterTest
    fun cleanup() {
        Log.dispatcher.clear()
    }

    private fun createClient(khronicleLogger: KhronicleKtorClientLogger): HttpClient {
        val engine = MockEngine { _ -> respond(RESPONSE) }
        return HttpClient(engine) {
            install(Logging) {
                level = ALL
                logger = khronicleLogger
            }
        }
    }

    @Test
    fun khroniclelogger_atDefaultLevel_logsVerbose() = runTest {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        val client = createClient(KhronicleKtorClientLogger())
        client.get(URL).body<String>()
        assertTrue { logger.verboseCalls.isNotEmpty() }
    }

    @Test
    fun khroniclelogger_atSpecificLevel_logsAtLevel() = runTest {
        for (level in LogLevel.entries) {
            val logger = CallListLogger()
            Log.dispatcher.install(logger)
            val client = createClient(KhronicleKtorClientLogger(level))
            client.get(URL).body<String>()
            assertTrue { logger.allCalls.any { it.level == level } }
            Log.dispatcher.clear()
        }
    }

    @Test
    fun khroniclelogger_withMetadataCallback_installsMetadata() = runTest {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        val client = createClient(KhronicleKtorClientLogger { metadata -> metadata[Sensitivity] = Sensitivity.Sensitive })
        client.get(URL).body<String>()
        assertTrue { logger.allCalls.any { it.metadata[Sensitivity] == Sensitivity.Sensitive } }
    }
}
