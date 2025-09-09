package data.mongo.mapper

import com.data.mongo.entity.QuizTopicEntity
import com.domain.model.quiz.QuizTopic


fun QuizTopicEntity.toQuizTopic(): QuizTopic{
    return QuizTopic(
        id = _id,
        titleArabic = titleArabic,
        titleEnglish = titleEnglish,
        subtitleArabic = subtitleArabic,
        subtitleEnglish = subtitleEnglish,
        masterOwnerId = masterOwnerId,
        ownersIds = ownersIds,
        viewsCount = viewsCount,
        disLikeCount = disLikeCount,
        likeCount = likeCount,
        imageUrl = imageUrl,
        tags = tags,
        topicColor = topicColor,
        playedCount = playedCount,
        quizTimeInMin = quizTimeInMin,
        isDeleted = isDeleted,
        isPublic = isPublic,
        createdAt = createdAt,
        updatedAt = updatedAt,

    )
}

fun QuizTopic.toQuizTopicEntity(): QuizTopicEntity{
    return QuizTopicEntity(
        titleArabic = titleArabic,
        titleEnglish = titleEnglish,
        subtitleArabic = subtitleArabic,
        subtitleEnglish = subtitleEnglish,
        masterOwnerId = masterOwnerId,
        ownersIds = ownersIds,
        viewsCount = viewsCount,
        disLikeCount = disLikeCount,
        likeCount = likeCount,
        imageUrl = imageUrl,
        tags = tags,
        topicColor = topicColor,
        playedCount = playedCount,
        quizTimeInMin = quizTimeInMin,
        isDeleted = isDeleted,
        isPublic = isPublic,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

