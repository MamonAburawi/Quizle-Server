package com.data.mongo.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserEntity(
    @BsonId
    val _id: String? = ObjectId().toString(),
    val userName: String,
    val email: String,
    val password: String,
    val phone: Long?,
    val accountType: String,
    val token: TokenEntity,
    val gender: String?,
    val imgProfile: String?,
    val favoriteTopicsIds: List<String>,
    val likedTopicsIds: List<String>,
    val disLikedTopicsIds: List<String>,
    val resultQuizziesIds: List<String>,
    val timeSpentQuizzingInMin: Int,
    val totalCorrectAnswers: Int,
    val totalQuizzes: Int,
    val countryCode: String,
    val language: String, // En, Ar
    val isActive: Boolean,
    val isPublic: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val settings: SettingsEntity
){

    data class TokenEntity(
        val accessToken: String,
        val expAt: Long,
        val createdAt: Long,
        val type: String // Barrier token
    )


    data class SettingsEntity(
        val enableNotificationApp: Boolean,
        val enableQuizTime: Boolean,
        val switchToCustomTimeInMin: Boolean,
        val customQuizTimeInMin: Int
    )
}