package com.juul.khronicle

import com.android.tools.lint.detector.api.Category.Companion.MESSAGES
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Incident
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope.Companion.JAVA_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import org.jetbrains.uast.UCallExpression

class WrongLogUsageDetector : Detector(), SourceCodeScanner {
    private val androidLogMethodToKhronicleLogMethod = mapOf(
        "v" to "verbose",
        "d" to "debug",
        "i" to "info",
        "w" to "warn",
        "e" to "error",
        "wtf" to "assert",
    )

    override fun getApplicableMethodNames() = listOf("v", "d", "i", "w", "e", "wtf")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator

        if (evaluator.isMemberInClass(method, "android.util.Log")) {
            context.report(
                Incident(
                    issue = ISSUE_LOG,
                    scope = node,
                    location = context.getLocation(node),
                    message = "Using 'android.util.Log' instead of 'com.juul.khronicle.Log'",
                    fix = quickFixIssueLog(node),
                ),
            )
            return
        }
    }

    private fun quickFixIssueLog(logCall: UCallExpression): LintFix {
        val arguments = logCall.valueArguments
        val methodName = logCall.methodName

        val tag = arguments[0].asSourceString()
        val message: String
        val throwable: String?

        when (arguments.size) {
            2 -> {
                val msgOrThrowable = arguments[1]
                val isString = msgOrThrowable.getExpressionType()?.isString() ?: true

                message = when {
                    isString -> msgOrThrowable.asSourceString()
                    else -> ""
                }

                throwable = when {
                    isString -> null
                    else -> msgOrThrowable.sourcePsi?.text
                }
            }

            3 -> {
                message = arguments[1].asSourceString()
                throwable = arguments[2].sourcePsi?.text
            }

            else -> {
                throw IllegalStateException("android.util.Log overloads should have 2 or 3 arguments")
            }
        }

        val khronicleLogMethodName = androidLogMethodToKhronicleLogMethod[methodName]

        val fixSource1 = buildString {
            append("com.juul.khronicle.Log.$khronicleLogMethodName(tag = $tag")
            if (throwable == null) {
                append(")")
            } else {
                append(", throwable = $throwable)")
            }

            append(" { $message }")
        }

        val fixSource2 = buildString {
            append("com.juul.khronicle.Log.$khronicleLogMethodName")
            if (throwable != null) {
                append("(throwable = $throwable)")
            }

            append(" { $message }")
        }

        val logCallSource = logCall.uastParent!!.sourcePsi?.text
        return fix()
            .group()
            .add(
                fix()
                    .replace()
                    .text(logCallSource)
                    .shortenNames()
                    .reformat(true)
                    .with(fixSource1)
                    .build(),
            ).add(
                fix()
                    .replace()
                    .text(logCallSource)
                    .shortenNames()
                    .reformat(true)
                    .with(fixSource2)
                    .build(),
            ).build()
    }

    companion object {
        @Suppress("ktlint:standard:max-line-length")
        val ISSUE_LOG = Issue.create(
            id = "LogNotKhronicle",
            briefDescription = "Logging call to android.util.Log instead of Khronicle",
            explanation = "Since Khronicle is included in the project, it is likely that calls to android.util.Log should instead be going to Khronicle.",
            category = MESSAGES,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(WrongLogUsageDetector::class.java, JAVA_FILE_SCOPE),
        )

        private fun PsiType.isString() = canonicalText == "java.lang.String"
    }
}
