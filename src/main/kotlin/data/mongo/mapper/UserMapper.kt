package com.data.mapper

import com.data.mongo.entity.UserEntity
import domain.model.User


fun UserEntity.toUser(): User{
    return User(
        id = _id,
        userName = userName,
        email = email,
        password = password,
        phone = phone,
        accountType = accountType,
        token = token.toToken(),
        gender = gender,
        imageProfile = imgProfile,
        favoriteTopicsIds = favoriteTopicsIds,
        likedTopicsIds = likedTopicsIds,
        disLikedTopicsIds = disLikedTopicsIds,
        resultQuizziesIds = resultQuizziesIds,
        timeSpentQuizzingInMin = timeSpentQuizzingInMin,
        totalCorrectAnswers = totalCorrectAnswers,
        totalQuizzes = totalQuizzes,
        countryCode = countryCode,
        language = language,
        isActive = isActive,
        isPublic = isPublic,
        createdAt = createdAt,
        updatedAt = updatedAt,
        settings = settings.toSettings()
    )
}

fun User.toUserEntity(): UserEntity{
    return UserEntity(
        userName = userName,
        email = email,
        password = password,
        phone = phone,
        accountType = accountType,
        token = token.toTokenEntity(),
        gender = gender,
        imgProfile = imageProfile,
        favoriteTopicsIds = favoriteTopicsIds,
        likedTopicsIds = likedTopicsIds,
        disLikedTopicsIds = disLikedTopicsIds,
        resultQuizziesIds = resultQuizziesIds,
        timeSpentQuizzingInMin = timeSpentQuizzingInMin,
        totalCorrectAnswers = totalCorrectAnswers,
        totalQuizzes = totalQuizzes,
        countryCode = countryCode,
        language = language,
        isActive = isActive,
        isPublic = isPublic,
        createdAt = createdAt,
        updatedAt = updatedAt,
        settings = settings.toSettingsEntity()

    )
}

