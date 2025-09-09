package data.mongo.repository.quiz

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.data.mongo.entity.QuizQuestionEntity
import com.data.mongo.entity.QuizTopicEntity
import data.mongo.mapper.toQuizTopic
import data.mongo.mapper.toQuizTopicEntity
import common.constant.MongoDBConstants
import com.domain.model.quiz.QuizTopic
import domain.repository.QuizTopicRepository
import domain.util.Result
import common.exception.AppException
import common.exception.DataException
import common.exception.DatabaseException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class TopicImpl(
    mongoDb: MongoDatabase
): QuizTopicRepository {

    val quizTopicCollection = mongoDb.getCollection<QuizTopicEntity>(MongoDBConstants.TOPIC_COLLECTION)
    private val questionCollection = mongoDb.getCollection<QuizQuestionEntity>(MongoDBConstants.QUESTIONS_COLLECTION)

    override suspend fun getTopicById(id: String?): Result<QuizTopic, AppException> {
        if (id.isNullOrBlank()) {
            Result.Failure(DataException.InvalidDataProvidedException())
        }
        return try {
            val topicQuery = Filters.eq(QuizTopicEntity::_id.name, id)
            val topicEntity = quizTopicCollection
                .find(filter = topicQuery)
                .firstOrNull()
            if (topicEntity != null) {
                Result.Success(topicEntity.toQuizTopic())
            } else {
                Result.Failure(DataException.TopicsNotFoundException())
            }

        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


    // this function will fetch only the topics that have questions.
    override suspend fun getQuizTopicsUsedInQuestions(): Result<List<QuizTopic>, AppException> {
        return try {

            val distinctTopicIds = questionCollection
                .distinct<String>(QuizQuestionEntity::topicId.name)
                .toList()

            if (distinctTopicIds.isEmpty()) {
                return  Result.Failure(DataException.TopicsNotFoundException())
            }

            val sort = Sorts.ascending(QuizTopicEntity::titleEnglish.name)
            val filter = Filters.`in`(QuizTopicEntity::_id.name, distinctTopicIds) // Assuming topicId in Question is String and matches QuizTopic's _id

            val topics = quizTopicCollection
                .find(filter)
                .sort(sort)
                .map { it.toQuizTopic() }
                .toList()

            if (topics.isNotEmpty()) {
                Result.Success(topics)
            } else {
                Result.Failure(DataException.TopicsNotFoundException())
            }
        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }




    override suspend fun getAllTopics(): Result<List<QuizTopic>, AppException> {
        return try {
            val sort = Sorts.ascending(QuizTopicEntity::titleEnglish.name)
            val topics = quizTopicCollection
                .find()
                .sort(sort)
                .map { it.toQuizTopic() }
                .toList()
            if (topics.isNotEmpty()){
                Result.Success(topics)
            }else {
                Result.Failure(DataException.TopicsNotFoundException())
            }
        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


    override suspend fun searchQuizTopic(
        titleEn: String?, titleAr: String?, subTitleEn: String?,
        subTitleAr: String?, tag: String?, limit: Int?
    ): Result<List<QuizTopic>, AppException> = try {

        val filterTitleEn = if (!titleEn.isNullOrEmpty())  Filters.regex(QuizTopicEntity::titleEnglish.name , titleEn, "i") else Filters.empty()
        val filterTitleAr = if (!titleAr.isNullOrEmpty())  Filters.regex(QuizTopicEntity::titleArabic.name , titleAr, "i") else Filters.empty()
        val filterSubTitleEn = if (!subTitleEn.isNullOrEmpty())  Filters.regex(QuizTopicEntity::subtitleEnglish.name , subTitleEn, "i") else Filters.empty()
        val filterSubTitleAr = if (!subTitleAr.isNullOrEmpty())  Filters.regex(QuizTopicEntity::subtitleArabic.name , subTitleAr, "i") else Filters.empty()
        val tag = if (!tag.isNullOrEmpty()) Filters.`in`(QuizTopicEntity::tags.name, listOf(tag)) else Filters.empty()

       // if you want to combine all fields in single query search
        val combinedFilter = Filters.or(filterTitleEn, filterTitleAr, filterSubTitleEn, filterSubTitleEn, filterSubTitleAr,tag)

      // if you want to search for each field separately,
      // val combinedFilter = Filters.and(filterTitleEn, filterTitleAr, filterSubTitleEn, filterSubTitleEn, filterSubTitleAr,tag)

        val findFlow = quizTopicCollection
            .find(combinedFilter)
            .let { if (limit != null && limit > 0) it.limit(limit) else it }

        val topics = findFlow
            .map { it.toQuizTopic() }
            .toList()

        if (topics.isNotEmpty()) Result.Success(topics) else  Result.Failure(DataException.TopicsNotFoundException())
    } catch (ex: Exception) {
        ex.printStackTrace() // Use proper logging in production
        Result.Failure(DatabaseException.UnknowErrorException())
    }



    override suspend fun deleteTopicById(id: String?): Result<Unit, AppException> {
        if (id.isNullOrBlank()) {
            Result.Failure(DataException.InvalidDataProvidedException())
        }
        return try {
            val filter = Filters.eq(QuizTopicEntity::_id.name, id)
            val isNotFound = quizTopicCollection.find(filter).firstOrNull() == null
            if (isNotFound){
                Result.Failure(DataException.TopicNotFoundException())
            }else {
                val isDeleted = quizTopicCollection.deleteOne(filter).wasAcknowledged()
                if (isDeleted){
                    Result.Success(Unit)
                }else {
                    Result.Failure(DatabaseException.UnknowErrorException())
                }
            }


        } catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }

    override suspend fun upsertTopic(quizTopic: QuizTopic): Result<Unit, AppException> {
        val id = quizTopic.id
        return try {
            if (id == null){ // insert new question
                val createdAt = System.currentTimeMillis()
                val topicEntity = quizTopic.toQuizTopicEntity().copy(createdAt = createdAt)
                quizTopicCollection.insertOne(topicEntity).wasAcknowledged()
            }else{
                val updateFilter = Filters.eq(QuizQuestionEntity::_id.name, id)
                val updatedAt = System.currentTimeMillis()
                val updateQuery = Updates.combine(
                    Updates.set(QuizTopic::titleEnglish.name, quizTopic.titleEnglish),
                    Updates.set(QuizTopic::titleArabic.name, quizTopic.titleArabic),
                    Updates.set(QuizTopic::subtitleEnglish.name, quizTopic.subtitleEnglish),
                    Updates.set(QuizTopic::subtitleArabic.name, quizTopic.subtitleArabic),
                    Updates.set(QuizTopic::viewsCount.name, quizTopic.viewsCount),
                    Updates.set(QuizTopic::disLikeCount.name, quizTopic.disLikeCount),
                    Updates.set(QuizTopic::likeCount.name, quizTopic.likeCount),
                    Updates.set(QuizTopic::tags.name, quizTopic.tags),
                    Updates.set(QuizTopic::playedCount.name, quizTopic.playedCount),
                    Updates.set(QuizTopic::quizTimeInMin.name, quizTopic.quizTimeInMin),
                    Updates.set(QuizTopic::isDeleted.name, quizTopic.isDeleted),
                    Updates.set(QuizTopic::isPublic.name, quizTopic.isPublic),
                    Updates.set(QuizTopic::imageUrl.name, quizTopic.imageUrl),
                    Updates.set(QuizTopic::topicColor.name, quizTopic.topicColor),
                    Updates.set(QuizTopic::updatedAt.name, updatedAt),
                    Updates.set(QuizTopic::ownersIds.name, quizTopic.ownersIds),
                    Updates.set(QuizTopic::masterOwnerId.name, quizTopic.masterOwnerId),

                )
                quizTopicCollection.updateOne(updateFilter, updateQuery).wasAcknowledged()
            }
            Result.Success(Unit)
        } catch (ex: Exception) {
            // Log the error
            ex.printStackTrace() // Use proper logging in production
          Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


}