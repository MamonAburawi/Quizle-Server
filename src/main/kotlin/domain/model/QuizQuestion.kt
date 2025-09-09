package com.domain.model.quiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class QuizQuestion(
   val id: String? = null,
   @SerialName("topic_Id") val topicId: String,
   @SerialName("question_text") val questionText: String,
   @SerialName("correct_answer") val correctAnswer: String,
   @SerialName("master_owner_Id") val masterOwnerId: String,
   @SerialName("incorrect_answers") val inCorrectAnswers: List<String>,
   @SerialName("owners_Ids")  val ownersIds: List<String> = emptyList(),
   @SerialName("img_url") val imageUrl: String? = null,
   @SerialName("created_at") val createdAt: Long = 0L,
   @SerialName("updated_at") val updatedAt: Long = 0L,
   @SerialName("report_count") val reportCount: Int = 0,
   @SerialName("level") val level: String = "", // Hard, Medium, Easy
   @SerialName("tags") val tags: List<String> = emptyList(),
   @SerialName("explanation") val explanation: String = ""
)



