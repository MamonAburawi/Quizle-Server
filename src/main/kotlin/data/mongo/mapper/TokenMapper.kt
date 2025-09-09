package com.data.mapper

import com.data.mongo.entity.UserEntity.TokenEntity
import domain.model.user.User.Token


fun Token.toTokenEntity(): TokenEntity {
    return TokenEntity(
        accessToken = accessToken,
        expAt = expAt,
        createdAt = createdAt,
        type = type
    )
}

fun TokenEntity.toToken(): Token {
    return Token(
        accessToken = accessToken,
        expAt = expAt,
        createdAt = createdAt,
        type = type
    )
}