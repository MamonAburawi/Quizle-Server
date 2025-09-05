package presentation.route.quiz_topic

import domain.repository.quiz.QuizTopicRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.searchQuizTopics(
    repository: QuizTopicRepository
){
    get<QuizTopicRoutePath.Search>{path ->
        repository.searchQuizTopic(
            titleAr = path.titleAr,
            titleEn = path.titleEn,
            subTitleAr = path.subTitleAr,
            subTitleEn = path.subTitleEn,
            limit = path.limit,
            tag = path.tag
        )
            .onSuccess { topics ->
               call.respondSuccess(data = topics)
            }
            .onFailure {
                call.respondError(it)
            }
    }
}


