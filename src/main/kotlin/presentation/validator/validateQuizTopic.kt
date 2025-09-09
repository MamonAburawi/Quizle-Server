package presentation.validator


import com.domain.model.quiz.QuizTopic
import presentation.validator.AppValidationRule.createInvalidResult
import io.ktor.server.plugins.requestvalidation.*


fun RequestValidationConfig.validateQuizTopic() {
    validate<QuizTopic> { currentTopic ->
        when {
            currentTopic.titleEnglish.isBlank() ->
                createInvalidResult(
                    messageEn = "English title must not be empty.",
                    messageAr = "العنوان الإنجليزي يجب ألا يكون فارغًا.",
                )
            currentTopic.titleArabic.isBlank() ->
                createInvalidResult(
                    messageEn = "Arabic title must not be empty.",
                    messageAr = "العنوان العربي يجب ألا يكون فارغًا.",
                )
            currentTopic.subtitleArabic.isBlank() ->
                createInvalidResult(
                    messageEn = "Arabic subtitle must not be empty.",
                    messageAr = "العنوان الفرعي العربي يجب ألا يكون فارغًا."
                )
            currentTopic.subtitleEnglish.isBlank() ->
                createInvalidResult(
                    messageEn = "English subtitle must not be empty.",
                    messageAr = "العنوان الفرعي الإنجليزي يجب ألا يكون فارغًا."
                )
            currentTopic.topicColor.isBlank() ->
                createInvalidResult(
                    messageEn = "Topic color must not be empty.",
                    messageAr = "لون الموضوع يجب ألا يكون فارغًا."
                )
            else ->
                ValidationResult.Valid
        }
    }
}

