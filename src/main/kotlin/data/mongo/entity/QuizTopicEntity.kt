package com.data.mongo.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class QuizTopicEntity(
    @BsonId val _id: String? = ObjectId().toString(),
    val titleArabic: String,
    val titleEnglish: String,
    val subtitleArabic: String,
    val subtitleEnglish: String,
    val masterOwnerId: String,
    val ownersIds: List<String>,
    val imageUrl: String,
    val tags: List<String>,
    val viewsCount: Int,
    val topicColor: String,
    val likeCount: Int,
    val disLikeCount: Int,
    val playedCount: Int,
    val quizTimeInMin: Int?,
    val isDeleted: Boolean,
    val isPublic: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)