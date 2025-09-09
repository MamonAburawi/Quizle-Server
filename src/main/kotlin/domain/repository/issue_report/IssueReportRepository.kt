package domain.repository.issue_report

import com.domain.model.issue_report.IssueReport
import domain.util.Result
import data.util.exception.AppException

interface IssueReportRepository {

    suspend fun getAllIssueReport(): Result<List<IssueReport>, AppException>
    suspend fun deleteIssueReportById(id: String?): Result<Unit, AppException>
    suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit, AppException>

}