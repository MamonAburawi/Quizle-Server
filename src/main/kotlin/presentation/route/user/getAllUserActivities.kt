package com.presentation.route.user

import domain.repository.user.LogEventRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import presentation.route.user.UserRoutePath
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.getAllUserActivity(
    repository: LogEventRepository
){
    get<UserRoutePath.UserActivity> { path ->
        repository.searchLogEvent(
            userId = path.userId,
            userName = path.userName,
            action = path.action,
            createdAt = path.createdAt,
            limit = path.limit)
            .onSuccess { userActivities ->
                call.respondSuccess(data = userActivities)
            }
            .onFailure {
                call.respondError(it)
            }

    }
}