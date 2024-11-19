package com.juul.khronicle

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class KhronicleIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(WrongLogUsageDetector.ISSUE_LOG)

    override val api: Int
        get() = CURRENT_API

    /**
     * works with Studio 4.0 or later; see
     * [com.android.tools.lint.detector.api.describeApi]
     */
    override val minApi: Int
        get() = 7

    override val vendor = Vendor(
        vendorName = "JuulLabs/Khronicle",
        identifier = "com.juul.khronicle:khronicle:{version}",
        feedbackUrl = "https://github.com/JuulLabs/khronicle/issues",
    )
}
