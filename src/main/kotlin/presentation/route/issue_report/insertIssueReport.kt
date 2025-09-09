package presentation.route.issue_report

import data.util.MongoDBConstants
import com.domain.model.issue_report.IssueReport
import domain.repository.issue_report.IssueReportRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.insertIssueReport(
    repository: IssueReportRepository
){
    authenticate(MongoDBConstants.JWT_CONFIGURATION_NAME) {
        post<IssueReportRoutePath> { path ->
            val issueReport = call.receive<IssueReport>()
            repository.insertIssueReport(issueReport = issueReport)
                .onSuccess {
                    call.respondSuccess<Unit>(
                        messageEn = "Issue report inserted successfully.",
                        messageAr = "تم إدراج تقرير المشكلة بنجاح.",
                        statusCode = HttpStatusCode.Created
                    )

                }
                .onFailure {
                    call.respondError(it)
                }
        }
    }

}