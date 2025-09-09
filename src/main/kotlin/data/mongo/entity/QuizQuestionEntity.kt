package com.data.mongo.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class QuizQuestionEntity(
    @BsonId
    val _id: String = ObjectId().toString(),
    val ownersIds: List<String>,
    val masterOwnerId: String,
    val imageUrl: String?,
    val topicId: String,
    val createdAt: Long,
    val updatedAt: Long,
    val questionText: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val reportCount: Int,
    val level: String,
    val tags: List<String>,
    val explanation: String
)