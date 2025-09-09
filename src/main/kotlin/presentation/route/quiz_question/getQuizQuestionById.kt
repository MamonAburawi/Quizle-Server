package presentation.route.quiz_question


import domain.repository.QuizQuestionRepository
import domain.util.onFailure
import domain.util.onSuccess
import domain.util.response.respondError
import domain.util.response.respondSuccess
import io.ktor.server.resources.*
import io.ktor.server.routing.Route


fun Route.getQuizQuestionById(repository: QuizQuestionRepository) {
    get<QuizQuestionRoutePath.ById>{ path ->
        repository.getQuizQuestion(path.questionId)
            .onSuccess { question ->
                call.respondSuccess(data = question)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}