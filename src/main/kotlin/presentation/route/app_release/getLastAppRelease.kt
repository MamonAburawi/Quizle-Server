package presentation.route.app_release

import domain.repository.AppReleaseInfoRepository
import com.presentation.route.app_release.AppReleasePath
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.*
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.getLastAppRelease(
    repository: AppReleaseInfoRepository
) {
    get<AppReleasePath.Last> { path ->
        repository.getLastReleaseInfo()
            .onSuccess {
                call.respondSuccess(data = it)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}