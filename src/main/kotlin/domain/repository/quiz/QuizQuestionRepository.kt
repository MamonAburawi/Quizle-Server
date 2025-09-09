package domain.repository.quiz

import com.domain.model.quiz.QuizQuestion
import domain.util.Result
import data.util.exception.AppException

interface QuizQuestionRepository {

    suspend fun getQuizQuestion(id: String?): Result<QuizQuestion, AppException>

    suspend fun getAllQuizQuestions(
        topicId: String?,
    ): Result<List<QuizQuestion>, AppException>

    suspend fun getRandomQuestions(
        topicId: String?,
        limit: Int?
    ): Result<List<QuizQuestion>, AppException>

    suspend fun insertQuestionBulk(list: List<QuizQuestion>): Result<Unit, AppException>

    suspend fun deleteQuizQuestion(id: String?): Result<Unit, AppException>
    suspend fun upsertQuizQuestion(question: QuizQuestion): Result<Unit, AppException>
}