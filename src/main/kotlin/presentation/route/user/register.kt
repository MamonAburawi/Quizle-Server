package presentation.route.user


import domain.model.User
import domain.repository.UserRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.register(
    userRepository: UserRepository
){
    post<UserRoutePath.Register> { path ->
        val user = call.receive<User>()
        userRepository.register(user = user)
            .onSuccess { registeredUser ->
                call.respondSuccess(data = registeredUser)
            }
            .onFailure {
                call.respondError(it)
            }

    }
}