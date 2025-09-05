package domain.repository.user

import com.domain.model.user.UserActivity
import domain.util.Result
import domain.util.exception.AppException

interface UserActivityRepository {


    suspend fun getAllUserActivities(
        userId: String?,
        action: String?,
        createdAt: Long?,
        userName: String?,
        limit: Int?
    ): Result<List<UserActivity>, AppException>


    suspend fun insertUserActivity(userActivity: UserActivity): Result<Unit, AppException>


}