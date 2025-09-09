package presentation.route.quiz_question

import io.ktor.resources.Resource


@Resource("/quiz/questions")
class QuizQuestionRoutePath(
    val topicId: String? = null, // optional data
) {
    // required data
    @Resource("{questionId}")
    data class ById(
        val parent: QuizQuestionRoutePath = QuizQuestionRoutePath(),
        val questionId: String
    )

    @Resource("bulk")
    data class Bulk(
        val parent: QuizQuestionRoutePath = QuizQuestionRoutePath(),
    )

    @Resource("random")
    data class Random(
        val parent: QuizQuestionRoutePath = QuizQuestionRoutePath(),
        val topicId: String? = null, // optional data
        val limit: Int? = null
    )

}