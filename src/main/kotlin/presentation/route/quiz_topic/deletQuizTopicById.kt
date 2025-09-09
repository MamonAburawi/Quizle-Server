package presentation.route.quiz_topic

import domain.repository.QuizTopicRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.deleteQuizTopicById(
   repository: QuizTopicRepository
){
    delete<QuizTopicRoutePath.ById>{path ->
        repository.deleteTopicById(path.topicId)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Quiz topic deleted successfully.",
                    messageAr = "تم حذف موضوع الاختبار بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}