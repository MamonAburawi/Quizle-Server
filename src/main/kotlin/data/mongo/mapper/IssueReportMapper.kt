package data.mongo.mapper

import com.data.entity.issue_report.IssueReportEntity
import com.domain.model.issue_report.IssueReport


fun IssueReportEntity.toIssueReport(): IssueReport{
    return IssueReport(
        id  = _id,
        questionId = questionId,
        issueType = issueType,
        additionalComment = additionalComment,
        userId = userId,
        createdAt = createdAt,
        isResolved = isResolved
    )
}

fun IssueReport.toIssueReportEntity(): IssueReportEntity{
    return IssueReportEntity(
        questionId = questionId,
        issueType = issueType,
        additionalComment = additionalComment,
        userId = userId,
        createdAt = createdAt,
        isResolved = isResolved
    )
}