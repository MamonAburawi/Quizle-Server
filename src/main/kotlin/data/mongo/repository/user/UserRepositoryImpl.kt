package data.mongo.repository.user


import com.data.mongo.entity.UserEntity
import com.data.mapper.toSettingsEntity
import com.data.mapper.toTokenEntity
import com.data.mapper.toUser
import com.data.mapper.toUserEntity
import domain.model.User
import domain.repository.UserRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.presentation.plugin.JWTService
import common.constant.MongoDBConstants.USER_COLLECTION

import domain.util.Result
import common.exception.AppException
import common.exception.AuthException
import common.exception.DataException
import common.exception.DatabaseException
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Duration.Companion.days

class UserRepositoryImpl(
    private val mongoDb: MongoDatabase,
    private val jwtService: JWTService
): UserRepository {
    private val userCollection = mongoDb
        .getCollection<UserEntity>(USER_COLLECTION)

    override suspend fun login(email: String, password: String, tokenExp: Long?): Result<User, AppException> {
        return try {
            val filter = Filters.eq(UserEntity::email.name, email)
            val userEntity = userCollection.find(filter).firstOrNull()
                ?: return Result.Failure( AuthException.UserNotExistsException())

            if (userEntity.password != password) {
                return Result.Failure(AuthException.InvalidCredentialsException())
            }

            val createdAt = System.currentTimeMillis()
            val expAt = tokenExp ?: (createdAt + 30.days.inWholeMilliseconds)
            val accessToken = jwtService.generateToken(email = userEntity.email, expAt = expAt)
            val newToken = User.Token(accessToken = accessToken, expAt = expAt, createdAt = createdAt)

            // Update the user entity and then the database
            val updatedUserEntity = userEntity.copy(token = newToken.toTokenEntity())
            userCollection.updateOne(
                filter = filter,
                update = Updates.set(UserEntity::token.name, newToken.toTokenEntity())
            )

            Result.Success(updatedUserEntity.toUser())
        }catch (ex: Exception){
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }

    }


    override suspend fun register(user: User): Result<User, AppException> {
        val createdAt = System.currentTimeMillis()
        return try {
            val filter = Filters.eq(UserEntity::email.name, user.email)
            val userEntity = userCollection
                .find(filter)
                .firstOrNull()
            if (userEntity?.email == user.email) {

                Result.Failure(AuthException.UserAlreadyExistsException())

            } else {
                val expAt = createdAt + 30.days.inWholeMilliseconds
                val accessToken = jwtService.generateToken(email = user.email, expAt = expAt, createdAt = createdAt)

                val token = User.Token(
                    accessToken = accessToken,
                    expAt = expAt,
                    createdAt = createdAt,
                )


                val updatedUser = user.copy(token = token)
                val entityUser = updatedUser.toUserEntity()
                val isInserted = userCollection.insertOne(entityUser).wasAcknowledged()
                if (isInserted) {
                    Result.Success(entityUser.toUser())
                } else {
                    Result.Failure(DatabaseException.UnknowErrorException())
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }

    override suspend fun getUserById(id: String): Result<User, AppException> {
        return try {
            if (id.isBlank() || id.isEmpty()){
                return Result.Failure(DataException.InvalidDataProvidedException())
            }

            val filter = Filters.eq(UserEntity::_id.name, id)
            val user = userCollection
                .find(filter)
                .firstOrNull()

            if (user != null) {
                Result.Success(user.toUser())

            } else {
                Result.Failure(DataException.UserNotFoundException())
            }

        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


    override suspend fun updateUser(user: User): Result<Unit, AppException> {
        return try {
            val id = user.id
            val updatedAt = System.currentTimeMillis()

            val existingUser = userCollection.find(Filters.eq(UserEntity::_id.name, id)).firstOrNull()

            if (existingUser != null) {

                val updateFilter = Filters.eq(UserEntity::_id.name, id)

                val updateQuery = Updates.combine(
                    Updates.set(UserEntity::userName.name, user.userName),
                    Updates.set(UserEntity::email.name, user.email),
                    Updates.set(UserEntity::password.name, user.password),
                    Updates.set(UserEntity::phone.name, user.phone),
                    Updates.set(UserEntity::token.name, user.token.toTokenEntity()),
                    Updates.set(UserEntity::gender.name, user.gender),
                    Updates.set(UserEntity::accountType.name, user.accountType),
                    Updates.set(UserEntity::imgProfile.name, user.imageProfile),
                    Updates.set(UserEntity::favoriteTopicsIds.name, user.favoriteTopicsIds),
                    Updates.set(UserEntity::likedTopicsIds.name, user.likedTopicsIds),
                    Updates.set(UserEntity::disLikedTopicsIds.name, user.disLikedTopicsIds),
                    Updates.set(UserEntity::resultQuizziesIds.name, user.resultQuizziesIds),
                    Updates.set(UserEntity::timeSpentQuizzingInMin.name, user.timeSpentQuizzingInMin),
                    Updates.set(UserEntity::totalCorrectAnswers.name, user.totalCorrectAnswers),
                    Updates.set(UserEntity::totalQuizzes.name, user.totalQuizzes),
                    Updates.set(UserEntity::countryCode.name, user.countryCode),
                    Updates.set(UserEntity::language.name, user.language),
                    Updates.set(UserEntity::isActive.name, user.isActive),
                    Updates.set(UserEntity::isPublic.name, user.isPublic),
                    Updates.set(UserEntity::updatedAt.name, updatedAt),
                    Updates.set(UserEntity::settings.name, user.settings.toSettingsEntity())
                )

                userCollection.updateOne(updateFilter, updateQuery)
                Result.Success(Unit)
            } else {
                Result.Failure(DataException.UserNotFoundException())
            }

        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }

    override suspend fun terminateSession(userId: String): Result<Unit, AppException> {
        return try {

            val existingUser = userCollection.find(Filters.eq(UserEntity::_id.name, userId)).firstOrNull()
            if (existingUser != null) {
                val updateFilter = Filters.eq(UserEntity::_id.name, userId)
                val token = existingUser.token.copy(
                    accessToken = ""
                )

                val updateQuery = Updates.combine(
                    Updates.set(UserEntity::token.name, token),
                )

                userCollection.updateOne(updateFilter, updateQuery)
                Result.Success(Unit)
            } else {
                Result.Failure(DataException.UserNotFoundException())
            }
        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


}


