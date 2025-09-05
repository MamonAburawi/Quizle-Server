package domain.repository.quiz


import com.domain.model.quiz.QuizTopic
import domain.util.Result
import domain.util.exception.AppException

interface QuizTopicRepository {

    suspend fun getQuizTopicById(id: String?): Result<QuizTopic, AppException>
    suspend fun getQuizTopicsUsedInQuestions(): Result<List<QuizTopic>, AppException>
    suspend fun deleteQuizTopicById(id: String?): Result<Unit, AppException>
    suspend fun upsertQuizTopic(quizTopic: QuizTopic): Result<Unit, AppException>
    suspend fun getAllQuizTopic(): Result<List<QuizTopic>, AppException>

    suspend fun searchQuizTopic(
        titleEn: String?,
        titleAr: String?,
        subTitleEn: String?,
        subTitleAr: String?,
        tag: String?,
        limit: Int?
    ): Result<List<QuizTopic>, AppException>

}