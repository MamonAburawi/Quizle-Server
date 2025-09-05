package presentation.route.user

import com.domain.model.user.UserActivity
import domain.repository.user.UserActivityRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.insertUserActivity(
    repository: UserActivityRepository
){
    post<UserRoutePath.AddActivity> { path ->
        val userActivity = call.receive<UserActivity>()
        repository.insertUserActivity(userActivity)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "User activity inserted successfully.",
                    messageAr = "تم إدراج نشاط المستخدم بنجاح.",
                    statusCode = HttpStatusCode.Created
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}