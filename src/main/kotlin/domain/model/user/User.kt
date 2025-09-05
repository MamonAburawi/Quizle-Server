package domain.model.user



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: String? = null,
    @SerialName("user_name") val userName: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("account_type") val accountType: String = "basic",
    @SerialName("phone") val phone: Long? = null,
    @SerialName("token") val token: Token,
    @SerialName("gender") val gender: String?,
    @SerialName("img_profile") val imageProfile: String? = null,
    @SerialName("favorite_topics_ids") val favoriteTopicsIds: List<String> = emptyList(),
    @SerialName("liked_topics_ids") val likedTopicsIds: List<String> = emptyList(),
    @SerialName("disliked_topics_ids") val disLikedTopicsIds: List<String> = emptyList(),
    @SerialName("result_quizzies_ids") val resultQuizziesIds: List<String> = emptyList(),
    @SerialName("time_spent_quizzing_in_min") val timeSpentQuizzingInMin: Int = 0,
    @SerialName("total_correct_answers") val totalCorrectAnswers: Int = 0,
    @SerialName("total_quizzes") val totalQuizzes: Int = 0,
    @SerialName("country_code") val countryCode: String = "",
    @SerialName("language") val language: String = "en",
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("is_public") val isPublic: Boolean = true,
    @SerialName("created_at") val createdAt: Long = 0L,
    @SerialName("updated_at") val updatedAt: Long = 0L,
    @SerialName("settings") val settings: Settings
) {
    @Serializable
    data class Token(
        @SerialName("access_token") val accessToken: String,
        @SerialName("exp_at") val expAt: Long,
        @SerialName("created_at") val createdAt: Long,
        @SerialName("type") val type: String = "bearer_token"
    )

    @Serializable
    data class Settings(
        @SerialName("enable_notification_app") val enableNotificationApp: Boolean = true,
        @SerialName("enable_quiz_time") val enableQuizTime: Boolean = true,
        @SerialName("switch_to_custom_time_in_min") val switchToCustomTimeInMin: Boolean = false,
        @SerialName("custom_quiz_time_in_min") val customQuizTimeInMin: Int = 0
    )
}