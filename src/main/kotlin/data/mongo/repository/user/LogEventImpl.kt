package data.mongo.repository.user

import com.data.mapper.toUser
import data.mongo.entity.LogEventEntity
import data.mongo.mapper.toLogEvent
import data.mongo.mapper.toLogEventEntity
import com.data.mongo.entity.UserEntity
import domain.model.user.LogEvent
import domain.repository.user.LogEventRepository
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import data.util.MongoDBConstants.USER_ACTIVITY_COLLECTION
import data.util.MongoDBConstants.USER_COLLECTION
import domain.util.Result
import data.util.exception.AppException
import data.util.exception.AuthException
import data.util.exception.DataException
import data.util.exception.DatabaseException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class LogEventImpl(
    private val mongoDb: MongoDatabase
): LogEventRepository {

    private val userActivityCollection = mongoDb
        .getCollection<LogEventEntity>(USER_ACTIVITY_COLLECTION)

    private val userCollection = mongoDb
        .getCollection<UserEntity>(USER_COLLECTION)

    override suspend fun searchLogEvent(
        userId: String?,
        action: String?,
        createdAt: Long?,
        userName: String?,
        limit: Int?
    ): Result<List<LogEvent>, AppException> {
        return try {
            val filterUserId = if (!userId.isNullOrEmpty())  Filters.eq(LogEventEntity::userId.name , userId) else Filters.empty()
            val filterAction = if (!action.isNullOrEmpty())  Filters.eq(LogEventEntity::action.name , action) else Filters.empty()
            val filterCreatedAt = if (createdAt != null && createdAt > 0)  Filters.eq(LogEventEntity::createdAt.name, createdAt) else Filters.empty()
            val filterUserName = if (!userName.isNullOrEmpty())  Filters.eq(LogEventEntity::userName.name , userName) else Filters.empty()


            val combinedFilter = Filters.and(filterUserId, filterAction, filterCreatedAt, filterUserName)

            val findFlow = userActivityCollection
                .find()
                .filter(combinedFilter)
                .let { if (limit != null && limit > 0) it.limit(limit) else it }

            val userActivities = findFlow
                .map { it.toLogEvent() }
                .toList()

            if (userActivities.isNotEmpty()) Result.Success(userActivities)
            else Result.Failure(DataException.UserActivitiesNotFoundException())
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
        }

    }


    override suspend fun logEvent(email: String, event: String): Result<Unit, AppException> {
       return try {
           val filter = Filters.eq(UserEntity::email.name, email)
           val createdAt = System.currentTimeMillis()
           val logEvent = LogEvent()

           val user = userCollection.find(filter).firstOrNull()?.toUser()
               ?: return Result.Failure( AuthException.UserNotExistsException())

           val logEventEntity = logEvent.toLogEventEntity()
               .copy(
                   createdAt = createdAt,
                   userId = user.id ?: "",
                   userName = user.userName,
                   action = event
               )
          val isInserted =  userActivityCollection.insertOne(logEventEntity).wasAcknowledged()
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