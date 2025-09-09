package presentation.route.quiz_question


import domain.repository.QuizQuestionRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.getAllQuizQuestions(repository: QuizQuestionRepository) {
    get<QuizQuestionRoutePath>{path ->
        repository.getAllQuizQuestions(topicId = path.topicId)
            .onSuccess { questions ->
                call.respondSuccess(data = questions)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}

