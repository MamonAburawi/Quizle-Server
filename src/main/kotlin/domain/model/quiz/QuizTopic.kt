package com.domain.model.quiz


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizTopic(
    val id: String? = null,
    @SerialName("title_Ar") val titleArabic: String,
    @SerialName("title_En") val titleEnglish: String,
    @SerialName("subtitle_Ar") val subtitleArabic: String,
    @SerialName("subtitle_En") val subtitleEnglish: String,
    @SerialName("owners_Ids") val ownersIds: List<String>,
    @SerialName("master_owner_Id") val masterOwnerId: String,
    @SerialName("topic_color") val topicColor: String,
    @SerialName("imgUrl") val imageUrl: String = "",
    @SerialName("tags")  val tags: List<String> = emptyList(),
    @SerialName("views_count") val viewsCount: Int = 0,
    @SerialName("like_count") val likeCount: Int = 0,
    @SerialName("dislike_count") val disLikeCount: Int = 0,
    @SerialName("played_count") val playedCount: Int = 0,
    @SerialName("quiz_time_min") val quizTimeInMin: Int? = null,
    @SerialName("is_deleted") val isDeleted: Boolean = false,
    @SerialName("is_public") val isPublic: Boolean = true,
    @SerialName("created_at") val createdAt: Long = 0L,
    @SerialName("updated_at") val updatedAt: Long = 0L
)

