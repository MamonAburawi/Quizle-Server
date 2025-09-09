package presentation.route.user

import domain.repository.user.UserRepository
import domain.util.onFailure
import domain.util.onSuccess
import domain.util.response.respondSuccess
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError



fun Route.login(
    userRepository: UserRepository
){
    post<UserRoutePath.Login> { path ->
        userRepository.login(email = path.email, password = path.password, tokenExp = path.tokenExp)
            .onSuccess { user ->
                call.respondSuccess(data = user)
            }
            .onFailure {
                call.respondError(it)
            }

    }
}