package presentation.validator

import com.domain.model.quiz.QuizQuestion
import presentation.validator.AppValidationRule.createInvalidResult
import io.ktor.server.plugins.requestvalidation.*


fun RequestValidationConfig.validateQuizQuestion() {
    validate<QuizQuestion> { quizQuestion ->
        when {

            quizQuestion.questionText.isBlank() || quizQuestion.questionText.length < 5 ->
                createInvalidResult(
                    messageEn = "Question must be at least 5 characters long and not empty.",
                    messageAr = "يجب أن يكون السؤال 5 أحرف على الأقل وغير فارغ."
                )

            quizQuestion.masterOwnerId.isBlank() ->
                createInvalidResult(
                    messageEn = "Master owner id should not be empty.",
                    messageAr = "يجب ألا يكون معرف المالك الرئيسي فارغا."
                )

            quizQuestion.correctAnswer.isBlank() ->
                createInvalidResult(
                    messageEn = "Correct answer must not be empty.",
                    messageAr = "يجب ألا تكون الإجابة الصحيحة فارغة."
                )

            quizQuestion.inCorrectAnswers.size < 3 ->
                createInvalidResult(
                    messageEn = "There must be at least 3 incorrect answers.",
                    messageAr = "يجب أن يكون هناك 3 إجابات خاطئة على الأقل."
                )

            quizQuestion.inCorrectAnswers.any { it.isBlank() } ->
                createInvalidResult(
                    messageEn = "Incorrect answers field must not be empty.",
                    messageAr = "يجب ألا يكون حقل الإجابات الخاطئة فارغ."
                )

            quizQuestion.topicId.isEmpty() ->
                createInvalidResult(
                    messageEn = "Topic code must not be empty.",
                    messageAr = "يجب أن تكون قيمة رمز الموضوع غير فارغة."
                )

            else -> ValidationResult.Valid
        }
    }
}
