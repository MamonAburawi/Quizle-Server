package data.mongo.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class LogEventEntity(
    @BsonId
    val _id: String? = ObjectId().toString(),
    val userName: String,
    val createdAt: Long,
    val action: String, // logout, login, register, start_quiz, complete_quiz
    val userId: String,
)