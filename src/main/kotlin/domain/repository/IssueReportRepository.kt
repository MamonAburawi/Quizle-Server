package domain.repository

import domain.model.IssueReport
import common.exception.AppException
import domain.util.Result

interface IssueReportRepository {

    suspend fun getAllIssueReport(): Result<List<IssueReport>, AppException>
    suspend fun deleteIssueReportById(id: String?): Result<Unit, AppException>
    suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit, AppException>

}