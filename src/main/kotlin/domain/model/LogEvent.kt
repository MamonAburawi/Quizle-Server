package domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class LogEvent(
    @SerialName("id") val id: String? = null,
    @SerialName("user_name") val userName: String = "",
    @SerialName("created_at") val createdAt: Long = 0L,
    @SerialName("action") val action: String = "",
    @SerialName("user_id") val userId: String = ""
)




