package data.mongo.repository.user

import com.data.entity.user.UserActivityEntity
import com.data.mapper.toUserActivity
import com.data.mapper.toUserActivityEntity
import com.domain.model.user.UserActivity
import domain.repository.user.UserActivityRepository
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import data.util.MongoDBConstants.USER_ACTIVITY_COLLECTION
import domain.util.Result
import domain.util.exception.AppException
import domain.util.exception.DataException
import domain.util.exception.DatabaseException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class UserActivityImpl(
    private val mongoDb: MongoDatabase
): UserActivityRepository {

    private val userActivityCollection = mongoDb
        .getCollection<UserActivityEntity>(USER_ACTIVITY_COLLECTION)


    override suspend fun getAllUserActivities(
        userId: String?,
        action: String?,
        createdAt: Long?,
        userName: String?,
        limit: Int?
    ): Result<List<UserActivity>, AppException> {
        return try {
            val filterUserId = if (!userId.isNullOrEmpty())  Filters.eq(UserActivityEntity::userId.name , userId) else Filters.empty()
            val filterAction = if (!action.isNullOrEmpty())  Filters.eq(UserActivityEntity::action.name , action) else Filters.empty()
            val filterCreatedAt = if (createdAt != null && createdAt > 0)  Filters.eq(UserActivityEntity::createdAt.name, createdAt) else Filters.empty()
            val filterUserName = if (!userName.isNullOrEmpty())  Filters.eq(UserActivityEntity::userName.name , userName) else Filters.empty()


            val combinedFilter = Filters.and(filterUserId, filterAction, filterCreatedAt, filterUserName)

            val findFlow = userActivityCollection
                .find()
                .filter(combinedFilter)
                .let { if (limit != null && limit > 0) it.limit(limit) else it }

            val userActivities = findFlow
                .map { it.toUserActivity() }
                .toList()

            if (userActivities.isNotEmpty()) Result.Success(userActivities)
            else Result.Failure(DataException.UserActivitiesNotFoundException())
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
        }

    }



    override suspend fun insertUserActivity(userActivity: UserActivity): Result<Unit, AppException> {
       return try {
           val createdAt = System.currentTimeMillis()
           val userActivityEntity = userActivity.toUserActivityEntity()
               .copy(createdAt = createdAt)
          val isInserted =  userActivityCollection.insertOne(userActivityEntity).wasAcknowledged()
           if (isInserted){
               Result.Success(Unit)
           }else {
               Result.Failure(DatabaseException.UnknowErrorException())
           }
       }catch (ex: Exception){
           ex.printStackTrace()
           Result.Failure(DatabaseException.UnknowErrorException())
       }
    }





}