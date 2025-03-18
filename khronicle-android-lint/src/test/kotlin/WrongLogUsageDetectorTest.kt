package com.juul.khronicle

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class WrongLogUsageDetectorTest {
    @Test
    fun usingAndroidLogWithTwoArgumentsFromJava() {
        lint()
            .files(
                java(
                    """
                    package foo;
                    import android.util.Log;
                    public class Example {
                      public void log() {
                        Log.d("TAG", "msg");
                      }
                    }
                    """,
                ).indented(),
                java(ANDROID_LOG_STUB),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.java:5: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    Log.d("TAG", "msg");
                |    ~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).verifyFixes()
            .expectFixDiffs(
                """
                |Fix for src/foo/Example.java line 5: Replace with com.juul.khronicle.Log.debug(tag = "TAG") { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log;
                |@@ -5 +6
                |-     Log.d("TAG", "msg");
                |+     Log.debug(tag = "TAG") { "msg" };
                |Fix for src/foo/Example.java line 5: Replace with com.juul.khronicle.Log.debug { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log;
                |@@ -5 +6
                |-     Log.d("TAG", "msg");
                |+     Log.debug { "msg" };
                """.trimMargin(),
            )
    }

    @Test
    fun usingAndroidLogWithTwoArgumentsFromKotlin() {
        lint()
            .files(
                java(ANDROID_LOG_STUB),
                kotlin(
                    """
                    package foo
                    import android.util.Log
                    class Example {
                      fun log() {
                        Log.d("TAG", "msg")
                      }
                    }
                    """,
                ).indented(),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.kt:5: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    Log.d("TAG", "msg")
                |    ~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).expectFixDiffs(
                """
                |Fix for src/foo/Example.kt line 5: Replace with com.juul.khronicle.Log.debug(tag = "TAG") { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log
                |@@ -5 +6
                |-     Log.d("TAG", "msg")
                |+     Log.debug(tag = "TAG") { "msg" }
                |Fix for src/foo/Example.kt line 5: Replace with com.juul.khronicle.Log.debug { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log
                |@@ -5 +6
                |-     Log.d("TAG", "msg")
                |+     Log.debug { "msg" }
                """.trimMargin(),
            )
    }

    @Test
    fun usingAndroidLogWithThreeArgumentsFromJava() {
        lint()
            .files(
                java(
                    """
                    package foo;
                    import android.util.Log;
                    public class Example {
                      public void log() {
                        Log.d("TAG", "msg", new Exception());
                      }
                    }
                    """,
                ).indented(),
                java(ANDROID_LOG_STUB),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.java:5: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    Log.d("TAG", "msg", new Exception());
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).verifyFixes()
            .expectFixDiffs(
                """
                |Fix for src/foo/Example.java line 5: Replace with com.juul.khronicle.Log.debug(tag = "TAG", throwable = new Exception()) { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log;
                |@@ -5 +6
                |-     Log.d("TAG", "msg", new Exception());
                |+     Log.debug(tag = "TAG", throwable = new Exception()) { "msg" };
                |Fix for src/foo/Example.java line 5: Replace with com.juul.khronicle.Log.debug(throwable = new Exception()) { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log;
                |@@ -5 +6
                |-     Log.d("TAG", "msg", new Exception());
                |+     Log.debug(throwable = new Exception()) { "msg" };
                """.trimMargin(),
            )
    }

    @Test
    fun usingAndroidLogWithThreeArgumentsFromKotlin() {
        lint()
            .files(
                java(ANDROID_LOG_STUB),
                kotlin(
                    """
                    package foo
                    import android.util.Log
                    class Example {
                      fun log() {
                        Log.d("TAG", "msg", Exception())
                      }
                    }
                    """,
                ).indented(),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.kt:5: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    Log.d("TAG", "msg", Exception())
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).expectFixDiffs(
                """
                |Fix for src/foo/Example.kt line 5: Replace with com.juul.khronicle.Log.debug(tag = "TAG", throwable = Exception()) { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log
                |@@ -5 +6
                |-     Log.d("TAG", "msg", Exception())
                |+     Log.debug(tag = "TAG", throwable = Exception()) { "msg" }
                |Fix for src/foo/Example.kt line 5: Replace with com.juul.khronicle.Log.debug(throwable = Exception()) { "msg" }:
                |@@ -3 +3
                |+ import com.juul.khronicle.Log
                |@@ -5 +6
                |-     Log.d("TAG", "msg", Exception())
                |+     Log.debug(throwable = Exception()) { "msg" }
                """.trimMargin(),
            )
    }

    @Test
    fun usingFullyQualifiedAndroidLogWithTwoArgumentsFromJava() {
        lint()
            .files(
                java(
                    """
                    package foo;
                    public class Example {
                      public void log() {
                        android.util.Log.d("TAG", "msg");
                      }
                    }
                    """,
                ).indented(),
                java(ANDROID_LOG_STUB),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.java:4: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    android.util.Log.d("TAG", "msg");
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).verifyFixes()
            .expectFixDiffs(
                """
                |Fix for src/foo/Example.java line 4: Replace with com.juul.khronicle.Log.debug(tag = "TAG") { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log;
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg");
                |+     Log.debug(tag = "TAG") { "msg" };
                |Fix for src/foo/Example.java line 4: Replace with com.juul.khronicle.Log.debug { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log;
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg");
                |+     Log.debug { "msg" };
                """.trimMargin(),
            )
    }

    @Test
    fun usingFullyQualifiedAndroidLogWithTwoArgumentsFromKotlin() {
        lint()
            .files(
                java(ANDROID_LOG_STUB),
                kotlin(
                    """
                    package foo
                    class Example {
                      fun log() {
                        android.util.Log.d("TAG", "msg")
                      }
                    }
                    """,
                ).indented(),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.kt:4: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    android.util.Log.d("TAG", "msg")
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).expectFixDiffs(
                """
                |Fix for src/foo/Example.kt line 4: Replace with com.juul.khronicle.Log.debug(tag = "TAG") { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg")
                |+     Log.debug(tag = "TAG") { "msg" }
                |Fix for src/foo/Example.kt line 4: Replace with com.juul.khronicle.Log.debug { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg")
                |+     Log.debug { "msg" }
                """.trimMargin(),
            )
    }

    @Test
    fun usingFullyQualifiedAndroidLogWithThreeArgumentsFromJava() {
        lint()
            .files(
                java(
                    """
                    package foo;
                    public class Example {
                      public void log() {
                        android.util.Log.d("TAG", "msg", new Exception());
                      }
                    }
                    """,
                ).indented(),
                java(ANDROID_LOG_STUB),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.java:4: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    android.util.Log.d("TAG", "msg", new Exception());
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).verifyFixes()
            .expectFixDiffs(
                """
                |Fix for src/foo/Example.java line 4: Replace with com.juul.khronicle.Log.debug(tag = "TAG", throwable = new Exception()) { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log;
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg", new Exception());
                |+     Log.debug(tag = "TAG", throwable = new Exception()) { "msg" };
                |Fix for src/foo/Example.java line 4: Replace with com.juul.khronicle.Log.debug(throwable = new Exception()) { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log;
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg", new Exception());
                |+     Log.debug(throwable = new Exception()) { "msg" };
                """.trimMargin(),
            )
    }

    @Test
    fun usingFullyQualifiedAndroidLogWithThreeArgumentsFromKotlin() {
        lint()
            .files(
                java(ANDROID_LOG_STUB),
                kotlin(
                    """
                    package foo
                    class Example {
                      fun log() {
                        android.util.Log.d("TAG", "msg", Exception())
                      }
                    }
                    """,
                ).indented(),
            ).issues(WrongLogUsageDetector.ISSUE_LOG)
            .allowMissingSdk()
            .run()
            .expect(
                """
                |src/foo/Example.kt:4: Warning: Using 'android.util.Log' instead of 'com.juul.khronicle.Log' [LogNotKhronicle]
                |    android.util.Log.d("TAG", "msg", Exception())
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |0 errors, 1 warnings
                """.trimMargin(),
            ).expectFixDiffs(
                """
                |Fix for src/foo/Example.kt line 4: Replace with com.juul.khronicle.Log.debug(tag = "TAG", throwable = Exception()) { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg", Exception())
                |+     Log.debug(tag = "TAG", throwable = Exception()) { "msg" }
                |Fix for src/foo/Example.kt line 4: Replace with com.juul.khronicle.Log.debug(throwable = Exception()) { "msg" }:
                |@@ -2 +2
                |+ import com.juul.khronicle.Log
                |@@ -4 +5
                |-     android.util.Log.d("TAG", "msg", Exception())
                |+     Log.debug(throwable = Exception()) { "msg" }
                """.trimMargin(),
            )
    }
}

private val ANDROID_LOG_STUB = """
    |package android.util;
    |
    |public final class Log {
    |    public static int v(String tag, String msg) {}
    |    public static int v(String tag, String msg, Throwable tr) {}
    |    public static int d(String tag, String msg) {}
    |    public static int d(String tag, String msg, Throwable tr) {}
    |    public static int i(String tag, String msg) {}
    |    public static int i(String tag, String msg, Throwable tr) {}
    |    public static int w(String tag, String msg) {}
    |    public static int w(String tag, String msg, Throwable tr) {}
    |    public static int e(String tag, String msg) {}
    |    public static int e(String tag, String msg, Throwable t) {}
    |    public static int wtf(String tag, String msg) {}
    |    public static int wtf(String tag, String msg, Throwable tr) {}
    |}
""".trimMargin()
