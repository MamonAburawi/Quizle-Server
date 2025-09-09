package data.mongo.repository.app_release

import com.data.mongo.entity.AppReleaseInfoEntity
import com.data.mapper.toAppReleaseInfo
import com.data.mapper.toAppReleaseInfoEntity
import com.domain.model.AppReleaseInfo
import domain.repository.app_release.AppReleaseInfoRepository
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import data.util.MongoDBConstants
import domain.util.Result
import data.util.exception.AppException
import data.util.exception.DataException
import data.util.exception.DatabaseException
import kotlinx.coroutines.flow.firstOrNull


class AppReleaseRepositoryImpl(
    private val mongoDb: MongoDatabase
): AppReleaseInfoRepository {
    private val appReleaseCollection = mongoDb.getCollection<AppReleaseInfoEntity>(MongoDBConstants.APP_RELEASE_COLLECTION)


    override suspend fun getLastReleaseInfo(): Result<AppReleaseInfo, AppException> {
        return try {
            val lastReleaseEntity = appReleaseCollection
                .find()
                .sort(Sorts.descending(AppReleaseInfoEntity::versionCode.name))
                .firstOrNull()

            if (lastReleaseEntity != null) {
                Result.Success(lastReleaseEntity.toAppReleaseInfo())
            } else {
                Result.Failure(DataException.AppReleaseNotFoundException())
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
//            Result.Failure(DataError.Database(messageEn = ex.localizedMessage))
        }
    }


    override suspend fun insertReleaseInfo(release: AppReleaseInfo): Result<Unit, AppException> {
        return try {
            val releaseDate = System.currentTimeMillis()
            val releaseInfoEntity = release.toAppReleaseInfoEntity()
                .copy( releaseDate = releaseDate)
            val isInserted = appReleaseCollection.insertOne(releaseInfoEntity).wasAcknowledged()
            if (isInserted){
                Result.Success(Unit)
            }else {
                Result.Failure(DatabaseException.UnknowErrorException())
            }
        }catch (ex: Exception) {
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
    }
    }

}