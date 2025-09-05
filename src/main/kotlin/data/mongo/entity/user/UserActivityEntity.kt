package com.data.entity.user

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId



data class UserActivityEntity(
    @BsonId
    val _id: String? = ObjectId().toString(),
    val userName: String,
    val createdAt: Long,
    val action: String, // logout, login, register, start_quiz, complete_quiz
    val userId: String,
)
