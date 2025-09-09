package domain.repository.user

import domain.model.user.LogEvent
import domain.util.Result
import data.util.exception.AppException

interface LogEventRepository {


    suspend fun searchLogEvent(
        userId: String?,
        action: String?,
        createdAt: Long?,
        userName: String?,
        limit: Int?
    ): Result<List<LogEvent>, AppException>


    suspend fun logEvent(email: String, event: String): Result<Unit, AppException>


}