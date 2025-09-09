package data.mongo.mapper

import com.data.mongo.entity.IssueReportEntity
import domain.model.IssueReport


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