package presentation.route.user

import domain.repository.user.UserRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.resources.post
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.terminateSession(
    userRepository: UserRepository
){
    post<UserRoutePath.TerminateSession> { path ->
        userRepository.terminateSession(userId = path.userId)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "User session has been terminated successfully.",
                    messageAr = "تم إنهاء جلسة المستخدم بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}