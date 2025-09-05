package com.data.mapper

import com.data.entity.user.UserActivityEntity
import com.domain.model.user.UserActivity

fun UserActivityEntity.toUserActivity(): UserActivity{
    return UserActivity(
        id = _id,
        userId = userId,
        userName = userName,
        createdAt = createdAt,
        action = action
    )
}




fun UserActivity.toUserActivityEntity(): UserActivityEntity{
    return UserActivityEntity(
        userId = userId,
        userName = userName,
        createdAt = createdAt,
        action = action
    )
}