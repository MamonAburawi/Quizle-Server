package presentation.route.quiz_topic

import domain.repository.quiz.QuizTopicRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.getAllQuizTopic(
    repository: QuizTopicRepository
){
    get<QuizTopicRoutePath>{path ->
        repository.getAllTopics()
            .onSuccess { topics ->
                call.respondSuccess(data = topics)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}