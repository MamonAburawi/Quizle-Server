package presentation.route.quiz_question


import domain.repository.quiz.QuizQuestionRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.deleteQuizQuestionById(repository: QuizQuestionRepository) {
    delete<QuizQuestionRoutePath.ById>{ path ->
        val questionId = path.questionId
        repository.deleteQuizQuestion(questionId)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Question with ID $questionId deleted successfully.",
                    messageAr = "تم حذف السؤال الذي يحمل المعرف $questionId بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}