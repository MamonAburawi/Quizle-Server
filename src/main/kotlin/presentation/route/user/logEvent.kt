package presentation.route.user

import domain.repository.user.LogEventRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.logEvent(
    repository: LogEventRepository
){
    post<UserRoutePath.AddActivity> { path ->
        repository.logEvent(email = path.email, event = path.action)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "User event has been recorded.",
                    messageAr = "تم تسجيل حدث المستخدم.",
                    statusCode = HttpStatusCode.Created
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}