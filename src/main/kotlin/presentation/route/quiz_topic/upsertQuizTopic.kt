package presentation.route.quiz_topic

import com.domain.model.quiz.QuizTopic
import domain.repository.quiz.QuizTopicRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.upsertQuizTopic(
    repository: QuizTopicRepository
){
    post<QuizTopicRoutePath>{path ->
        val topic = call.receive<QuizTopic>()
        repository.upsertTopic(quizTopic = topic)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Quiz topic created successfully.",
                    messageAr = "تم إنشاء موضوع الاختبار بنجاح.",
                    statusCode = HttpStatusCode.Created
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}
