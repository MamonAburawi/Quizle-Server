package presentation.route.issue_report

import io.ktor.resources.Resource

@Resource("/issue/report")
class IssueReportRoutePath {
    @Resource("{reportId}")
    data class ById(
        val parent: IssueReportRoutePath = IssueReportRoutePath(),
        val reportId: String
    )
}