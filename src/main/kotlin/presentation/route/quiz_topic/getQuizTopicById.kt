package presentation.route.quiz_topic

import domain.repository.QuizTopicRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.getQuizTopicById(
    repository: QuizTopicRepository
){
    get<QuizTopicRoutePath.ById>{path ->
        repository.getTopicById(path.topicId)
            .onSuccess { topic ->
                call.respondSuccess(data = topic)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}