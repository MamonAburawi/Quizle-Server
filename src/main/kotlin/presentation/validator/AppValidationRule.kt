package presentation.validator

import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object AppValidationRule {

    @Serializable
    private data class InvalidRequest(
        val messageEn: String,
        val messageAr: String
    )

    fun createInvalidResult(messageEn: String, messageAr: String): ValidationResult.Invalid {
        val invalidRequest = InvalidRequest(
            messageEn = messageEn,
            messageAr = messageAr
        )
        val json = Json { prettyPrint = true }
        return ValidationResult.Invalid(
            reason = json.encodeToString(invalidRequest)
        )
    }

}
