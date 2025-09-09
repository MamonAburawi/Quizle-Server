package data.mongo.repository.quiz


import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.data.mongo.entity.QuizQuestionEntity
import data.mongo.mapper.toQuizQuestion
import data.mongo.mapper.toQuizQuestionEntity
import data.util.MongoDBConstants
import com.domain.model.quiz.QuizQuestion
import domain.repository.quiz.QuizQuestionRepository
import domain.util.Result
import data.util.exception.AppException
import data.util.exception.DataException
import data.util.exception.DatabaseException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson


class QuestionImpl(
    mongoDb: MongoDatabase
): QuizQuestionRepository {

    private val questionCollection = mongoDb
        .getCollection<QuizQuestionEntity>(MongoDBConstants.QUESTIONS_COLLECTION)

    override suspend fun getRandomQuestions(
        topicId: String?,
        limit: Int?
    ): Result<List<QuizQuestion>, AppException> = try {
        val questionLimit = limit?.takeIf { it > 0 } ?: 10

        val pipeline = mutableListOf<Bson>().apply {
            topicId?.let { add(Aggregates.match(Filters.eq(QuizQuestionEntity::topicId.name, it))) }
            add(Aggregates.sample(questionLimit))
        }

        val result = questionCollection
            .aggregate(pipeline)
            .map { it.toQuizQuestion() }
            .toList()

        if (result.isNotEmpty()) Result.Success(result)
        else Result.Failure(DataException.QuestionsNotFoundException())

    } catch (ex: Exception) {
        ex.printStackTrace() // Use proper logging in production
        Result.Failure(DatabaseException.UnknowErrorException())
//        Result.Failure(DataError.Database(
//            messageEn = ex.localizedMessage
//        ))
    }

    override suspend fun insertQuestionBulk(list: List<QuizQuestion>): Result<Unit, AppException> {
         return try {
             val createdAt = System.currentTimeMillis()
             val questions = list.map { it.copy(createdAt = createdAt).toQuizQuestionEntity() }
             questionCollection.insertMany(questions).wasAcknowledged()
             Result.Success(Unit)
         }catch (ex: Exception){
             ex.printStackTrace() // Use proper logging in production
             Result.Failure(DatabaseException.UnknowErrorException())
         }
    }

    override suspend fun getQuizQuestion(id: String?): Result<QuizQuestion, AppException> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataException.InvalidDataProvidedException())
        }
         return try {
            val filter = Filters.eq(QuizQuestionEntity::_id.name, id)
            val question = questionCollection.find(filter).firstOrNull()?.toQuizQuestion()
             if (question != null){
                 Result.Success(question)
             }else {
                 Result.Failure(DataException.QuestionNotFoundException())
             }
        }catch (ex: Exception){
             ex.printStackTrace() // Use proper logging in production
             Result.Failure(DatabaseException.UnknowErrorException())
        }
    }

    // todo: add flag and delete_at
    override suspend fun deleteQuizQuestion(id: String?): Result<Unit, AppException> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataException.InvalidDataProvidedException())
        }
        return try {
            val filter = Filters.eq(QuizQuestionEntity::_id.name, id)
            val isNotFound = questionCollection.find(filter).firstOrNull() == null
            if (isNotFound){
                Result.Failure(DataException.QuestionNotFoundException())
            }else {
                questionCollection.deleteOne(filter)
                Result.Success(Unit)
            }
        }catch (ex: Exception){
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }

    }




    override suspend fun upsertQuizQuestion(question: QuizQuestion): Result<Unit, AppException> {
        val id = question.id
        return try {
            if (id == null){ // insert new question
                val createdAt = System.currentTimeMillis()
                val questionEntity = question.toQuizQuestionEntity().copy(createdAt = createdAt)
                 questionCollection.insertOne(questionEntity).wasAcknowledged()
            }else{
                val updateFilter = Filters.eq(QuizQuestionEntity::_id.name, id)
                val updateQuery = Updates.combine(
                    Updates.set(QuizQuestionEntity::questionText.name, question.questionText),
                    Updates.set(QuizQuestionEntity::correctAnswer.name, question.correctAnswer),
                    Updates.set(QuizQuestionEntity::incorrectAnswers.name, question.inCorrectAnswers),
                    Updates.set(QuizQuestionEntity::explanation.name, question.explanation),
                    Updates.set(QuizQuestionEntity::topicId.name, question.topicId),
                    Updates.set(QuizQuestionEntity::tags.name, question.tags),
                    Updates.set(QuizQuestionEntity::level.name, question.level),
                    Updates.set(QuizQuestionEntity::reportCount.name, question.reportCount),
                    Updates.set(QuizQuestionEntity::updatedAt.name, question.updatedAt),
                    Updates.set(QuizQuestionEntity::imageUrl.name, question.imageUrl),
                    Updates.set(QuizQuestionEntity::ownersIds.name, question.ownersIds),
                    Updates.set(QuizQuestionEntity::masterOwnerId.name, question.masterOwnerId),
                )
                questionCollection.updateOne(updateFilter, updateQuery).wasAcknowledged()
            }
            Result.Success(Unit)
        } catch (ex: Exception) {
            // Log the error
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }

    override suspend fun getAllQuizQuestions(topicId: String?): Result<List<QuizQuestion>, AppException> {
        return try {
            val topicCodeFilter = topicId?.let {
                Filters.eq(QuizQuestionEntity::topicId.name, topicId)
            } ?: Filters.empty()
            val result = questionCollection
                .find(filter = topicCodeFilter)
                .map { it.toQuizQuestion() }
                .toList()

            if (result.isNotEmpty()){
                Result.Success(result)
            }else {
                Result.Failure(DataException.QuestionsNotFoundException())
            }
        }catch (ex: Exception) {
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


}