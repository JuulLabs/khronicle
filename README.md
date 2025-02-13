[![Slack](https://img.shields.io/badge/Slack-%23juul--libraries-ECB22E.svg?logo=data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIDAgNTQgNTQiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj48cGF0aCBkPSJNMTkuNzEyLjEzM2E1LjM4MSA1LjM4MSAwIDAgMC01LjM3NiA1LjM4NyA1LjM4MSA1LjM4MSAwIDAgMCA1LjM3NiA1LjM4Nmg1LjM3NlY1LjUyQTUuMzgxIDUuMzgxIDAgMCAwIDE5LjcxMi4xMzNtMCAxNC4zNjVINS4zNzZBNS4zODEgNS4zODEgMCAwIDAgMCAxOS44ODRhNS4zODEgNS4zODEgMCAwIDAgNS4zNzYgNS4zODdoMTQuMzM2YTUuMzgxIDUuMzgxIDAgMCAwIDUuMzc2LTUuMzg3IDUuMzgxIDUuMzgxIDAgMCAwLTUuMzc2LTUuMzg2IiBmaWxsPSIjMzZDNUYwIi8+PHBhdGggZD0iTTUzLjc2IDE5Ljg4NGE1LjM4MSA1LjM4MSAwIDAgMC01LjM3Ni01LjM4NiA1LjM4MSA1LjM4MSAwIDAgMC01LjM3NiA1LjM4NnY1LjM4N2g1LjM3NmE1LjM4MSA1LjM4MSAwIDAgMCA1LjM3Ni01LjM4N20tMTQuMzM2IDBWNS41MkE1LjM4MSA1LjM4MSAwIDAgMCAzNC4wNDguMTMzYTUuMzgxIDUuMzgxIDAgMCAwLTUuMzc2IDUuMzg3djE0LjM2NGE1LjM4MSA1LjM4MSAwIDAgMCA1LjM3NiA1LjM4NyA1LjM4MSA1LjM4MSAwIDAgMCA1LjM3Ni01LjM4NyIgZmlsbD0iIzJFQjY3RCIvPjxwYXRoIGQ9Ik0zNC4wNDggNTRhNS4zODEgNS4zODEgMCAwIDAgNS4zNzYtNS4zODcgNS4zODEgNS4zODEgMCAwIDAtNS4zNzYtNS4zODZoLTUuMzc2djUuMzg2QTUuMzgxIDUuMzgxIDAgMCAwIDM0LjA0OCA1NG0wLTE0LjM2NWgxNC4zMzZhNS4zODEgNS4zODEgMCAwIDAgNS4zNzYtNS4zODYgNS4zODEgNS4zODEgMCAwIDAtNS4zNzYtNS4zODdIMzQuMDQ4YTUuMzgxIDUuMzgxIDAgMCAwLTUuMzc2IDUuMzg3IDUuMzgxIDUuMzgxIDAgMCAwIDUuMzc2IDUuMzg2IiBmaWxsPSIjRUNCMjJFIi8+PHBhdGggZD0iTTAgMzQuMjQ5YTUuMzgxIDUuMzgxIDAgMCAwIDUuMzc2IDUuMzg2IDUuMzgxIDUuMzgxIDAgMCAwIDUuMzc2LTUuMzg2di01LjM4N0g1LjM3NkE1LjM4MSA1LjM4MSAwIDAgMCAwIDM0LjI1bTE0LjMzNi0uMDAxdjE0LjM2NEE1LjM4MSA1LjM4MSAwIDAgMCAxOS43MTIgNTRhNS4zODEgNS4zODEgMCAwIDAgNS4zNzYtNS4zODdWMzQuMjVhNS4zODEgNS4zODEgMCAwIDAtNS4zNzYtNS4zODcgNS4zODEgNS4zODEgMCAwIDAtNS4zNzYgNS4zODciIGZpbGw9IiNFMDFFNUEiLz48L2c+PC9zdmc+&labelColor=611f69)](https://kotlinlang.slack.com/messages/juul-libraries/)
[![codecov](https://codecov.io/gh/JuulLabs/khronicle/graph/badge.svg?token=8MHVBLNXB7)](https://codecov.io/gh/JuulLabs/khronicle)

# Khronicle

Simple and robust Kotlin Multiplatform logging.

### Setup (Gradle)

Add the core Khronicle dependency in your Gradle configuration:

```kotlin
implementation("com.juul.khronicle:khronicle-core:$version")
```

### Initialization

Logging can be initialized via `install`:

```kotlin
Log.dispatcher.install(ConsoleLogger)
```

If no `Logger` is installed, then log blocks are not called at runtime.

Custom loggers can be created by implementing the `Logger` interface.

#### Apple (NSLog)

Log to the Apple System Log by installing the `AppleSystemLogger`.

```kotlin
Log.dispatcher.install(AppleSystemLogger)
```

### Log

Log message can be logged via:

```kotlin
Log.verbose { "Example" }
```

The following [log level] functions are available:
- `verbose`
- `debug`
- `info`
- `warn`
- `error`
- `assert`

Optional `tag` and `throwable` may be provided. `tag` defaults to an autogenerated value, but behavior can be customized
via `Log.tagGenerator` property.

### Metadata

Khronicle supports associating arbitrary metadata to each log. A `WriteMetadata` instance is passed to each of
the logging functions above.

```kotlin
Log.verbose { metadata ->
    metadata[Sensitivity] = Sensitivity.Sensitive
    "My social security number is 123 45 6789"
}
```

This can be read inside of a `Logger` via the `ReadMetadata` passed into it.

```kotlin
class SampleLogger : Logger {
    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (metadata[Sensitivity] != Sensitivity.Sensitive) {
            // report to a destination that cannot include sensitive data
        }
    }

    // ...
}
```

To create your own metadata fields, use an `object` that implements the `Key` interface. The value of the generic
controls the type of the value in the metadata map.

```kotlin
object YourMetadata : Key<String>
```

A helpful pattern for many types is using the `companion object` as the metadata key.

```kotlin
enum class Sample {
    A, B, C;
    companion object : Key<Sample>
}
```

### Filtering

Khronicle `Logger`s may filter logs directly in their implementation, or may have filtering added after the fact via decorators.
This decorator pattern is especially useful when filtering pre-existing `Logger`s such as the built-in `ConsoleLogger`.

#### Log Level Filters

Log level filters are installed with `Logger.withMinimumLogLevel`. Because the filtering is based on which log call is
made, instead of the content of the log call, these can be used as an optimization: if all `Logger`s installed in the
root `DispatchLogger` have a minimum log level higher than the log call being made, then the log block is never called.

```kotlin
Log.dispatcher.install(
    ConsoleLogger
        .withMinimumLogLevel(LogLevel.Warn)
)

Log.debug { "This is not called." }
Log.warn { "This still gets called." }
```

#### Log Content Filters

Log content filters are installed with `Logger.withFilter`, and have full access to the content of a log.


```kotlin
Log.dispatcher.install(
    ConsoleLogger
        .withFilter { tag, message, metadata, throwable ->
            metadata[Sensitivity] == Sensitivity.NotSensitive
        }
)

Log.debug { "This block is evaluated, but does not get printed to the console." }
Log.warn { metadata ->
    metadata[Sensitivity] = Sensitivity.NotSensitive
    "This is also evaluated, and does print to the console."
}
```

### [Ktor]

A basic [Ktor] [`Logger`] is available via the `com.juul.khronicle:khronicle-ktor-client:$version` artifact.

To route a [Ktor] [`HttpClient`] instance's logs to Khronicle for logging, simply set
`KhronicleKtorClientLogger` as the [`HttpClient`]'s logger:

```kotlin
HttpClient {
    install(Logging) {
        logger = KhronicleKtorClientLogger()
    }
}
```

This will cause the [`HttpClient`]'s logs to be sent to installed Khronicle log dispatchers
(that were installed via `Log.dispatcher.install`).


[Ktor]: https://ktor.io/
[`HttpClient`]: https://api.ktor.io/ktor-client/ktor-client-core/io.ktor.client/-http-client/index.html
[`Logger`]: https://api.ktor.io/ktor-utils/io.ktor.util.logging/-logger/index.html
