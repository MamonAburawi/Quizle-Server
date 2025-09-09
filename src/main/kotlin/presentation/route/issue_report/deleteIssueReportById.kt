package presentation.route.issue_report

import domain.repository.IssueReportRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.deleteIssueReportById(
    repository: IssueReportRepository
) {
    delete<IssueReportRoutePath.ById> { path ->
        repository.deleteIssueReportById(id = path.reportId)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Issue report deleted successfully.",
                    messageAr = "تم حذف تقرير المشكلة بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}