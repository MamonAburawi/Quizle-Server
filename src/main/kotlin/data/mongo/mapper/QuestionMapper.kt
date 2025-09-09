package data.mongo.mapper

import com.data.mongo.entity.QuizQuestionEntity
import com.domain.model.quiz.QuizQuestion



fun QuizQuestionEntity.toQuizQuestion(): QuizQuestion{
    return QuizQuestion(
        id = _id,
        ownersIds = ownersIds,
        masterOwnerId = masterOwnerId,
        level = level,
        topicId = topicId,
        questionText = questionText,
        correctAnswer = correctAnswer,
        inCorrectAnswers = incorrectAnswers,
        imageUrl = imageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
        reportCount = reportCount,
        tags = tags,
        explanation = explanation
    )
}

fun QuizQuestion.toQuizQuestionEntity(): QuizQuestionEntity {
    return QuizQuestionEntity(
        ownersIds = ownersIds,
        masterOwnerId = masterOwnerId,
        level = level,
        topicId = topicId,
        questionText = questionText,
        correctAnswer = correctAnswer,
        incorrectAnswers = inCorrectAnswers,
        imageUrl = imageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt,
        reportCount = reportCount,
        tags = tags,
        explanation = explanation
    )
}


