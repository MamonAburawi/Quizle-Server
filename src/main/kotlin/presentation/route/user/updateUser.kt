package presentation.route.user

import domain.model.User
import domain.repository.UserRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.resources.patch
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.updateUser(
    userRepository: UserRepository,
){
    patch<UserRoutePath.Update> { path ->
        val user = call.receive<User>()

        userRepository.updateUser(user = user)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "User data has been updated successfully.",
                    messageAr = "تم تحديث بيانات المستخدم بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}