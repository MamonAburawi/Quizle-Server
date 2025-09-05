package presentation.route.quiz_question


import com.domain.model.quiz.QuizQuestion
import domain.repository.quiz.QuizQuestionRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.insertQuestionBulk(repository: QuizQuestionRepository) {
    post<QuizQuestionRoutePath.Bulk>{
        val question = call.receive<List<QuizQuestion>>()
        repository.insertQuestionBulk(question)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Questions inserted successfully.",
                    messageAr = "تم إدراج الأسئلة بنجاح.",
                    statusCode  = HttpStatusCode.Created
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}