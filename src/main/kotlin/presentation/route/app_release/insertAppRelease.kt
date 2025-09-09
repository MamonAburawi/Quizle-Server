package presentation.route.app_release


import com.domain.model.AppReleaseInfo
import com.presentation.route.app_release.AppReleasePath
import domain.repository.AppReleaseInfoRepository
import domain.util.onFailure
import domain.util.onSuccess
import domain.util.response.respondSuccess
import io.ktor.server.request.receive
import io.ktor.server.resources.*
import io.ktor.server.routing.Route
import domain.util.response.respondError


fun Route.insetAppRelease(
    repository: AppReleaseInfoRepository
) {
    post<AppReleasePath> { path ->
        val release = call.receive<AppReleaseInfo>()
        repository.insertReleaseInfo(release)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "App release inserted successfully.",
                    messageAr = "تم اضافة نسخة من التطبيق بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}