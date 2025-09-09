package domain.repository.quiz


import com.domain.model.quiz.QuizTopic
import domain.util.Result
import data.util.exception.AppException

interface QuizTopicRepository {

    suspend fun getTopicById(id: String?): Result<QuizTopic, AppException>
    suspend fun getQuizTopicsUsedInQuestions(): Result<List<QuizTopic>, AppException>
    suspend fun deleteTopicById(id: String?): Result<Unit, AppException>
    suspend fun upsertTopic(quizTopic: QuizTopic): Result<Unit, AppException>
    suspend fun getAllTopics(): Result<List<QuizTopic>, AppException>

    suspend fun searchQuizTopic(
        titleEn: String?,
        titleAr: String?,
        subTitleEn: String?,
        subTitleAr: String?,
        tag: String?,
        limit: Int?
    ): Result<List<QuizTopic>, AppException>

}