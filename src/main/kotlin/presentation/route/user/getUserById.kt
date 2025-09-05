package presentation.route.user

import domain.repository.user.UserRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess



fun Route.getUserById(
    userRepository: UserRepository
){
    get<UserRoutePath.ById> { path ->
        userRepository.getUserById(id = path.userId)
            .onSuccess { user ->
                call.respondSuccess(data = user)
            }
            .onFailure {
                call.respondError(it)
            }

    }
}