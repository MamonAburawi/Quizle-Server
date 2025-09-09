package com.domain.model.issue_report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueReport(
    @SerialName("id") val id: String? = null,
    @SerialName("question_id") val questionId: String,
    @SerialName("issue_type") val issueType: String,
    @SerialName("additional_comment") val additionalComment: String? = null,
    @SerialName("user_id") val userId: String,
    @SerialName("create_at") val createdAt: Long,
    @SerialName("is_resolved") val isResolved: Boolean? = false
)