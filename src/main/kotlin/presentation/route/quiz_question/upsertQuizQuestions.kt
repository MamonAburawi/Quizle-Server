package presentation.route.quiz_question


import com.domain.model.quiz.QuizQuestion
import domain.repository.QuizQuestionRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.upsertQuizQuestions(repository: QuizQuestionRepository) {
    post<QuizQuestionRoutePath>{
        val question = call.receive<QuizQuestion>()
        repository.upsertQuizQuestion(question)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Question created successfully.",
                    messageAr = "تم إنشاء السؤال بنجاح.",
                    statusCode = HttpStatusCode.Created
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }

}