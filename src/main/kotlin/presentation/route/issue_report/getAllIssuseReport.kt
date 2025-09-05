package presentation.route.issue_report

import domain.repository.issue_report.IssueReportRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.getAllIssueReport(
    repository: IssueReportRepository
){
    get<IssueReportRoutePath>{path->
        repository.getAllIssueReport()
            .onSuccess { reports ->
                call.respondSuccess(data =  reports)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}