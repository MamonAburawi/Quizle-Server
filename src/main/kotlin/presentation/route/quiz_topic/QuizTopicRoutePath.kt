package presentation.route.quiz_topic

import io.ktor.resources.Resource


@Resource("/quiz/topic")
class QuizTopicRoutePath() {

    @Resource("/{topicId}")
    data class ById(
        val parent: QuizTopicRoutePath = QuizTopicRoutePath(),
        val topicId: String
    )

    // Route for getting quiz topics that have associated questions
    @Resource("/active")
    data class ActiveTopics(
        val parent: QuizTopicRoutePath = QuizTopicRoutePath()
    )

    @Resource("/search")
    data class Search(
        val parent: QuizTopicRoutePath = QuizTopicRoutePath(),
        val titleEn: String? = null,
        val titleAr: String? = null,
        val subTitleEn: String? = null,
        val subTitleAr: String? = null,
        val tag: String? = null,
        val limit: Int? = null
    )






}