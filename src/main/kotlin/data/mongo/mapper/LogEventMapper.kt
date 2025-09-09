package data.mongo.mapper

import data.mongo.entity.LogEventEntity
import domain.model.user.LogEvent

fun LogEventEntity.toLogEvent(): LogEvent{
    return LogEvent(
        id = _id,
        userId = userId,
        userName = userName,
        createdAt = createdAt,
        action = action
    )
}




fun LogEvent.toLogEventEntity(): LogEventEntity{
    return LogEventEntity(
        userId = userId,
        userName = userName,
        createdAt = createdAt,
        action = action
    )
}