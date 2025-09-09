package presentation.route.quiz_question

import domain.repository.quiz.QuizQuestionRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.getRandomQuestions(repository: QuizQuestionRepository) {
    get<QuizQuestionRoutePath.Random>{ path ->
        repository.getRandomQuestions(path.topicId, path.limit)
            .onSuccess { questions ->
                call.respondSuccess(data = questions)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}

