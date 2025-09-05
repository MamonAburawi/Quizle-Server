package domain.util.response

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.acceptLanguage
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable

// Reusable data class for a simple message response
@Serializable
data class MessageResponse(val message: String)

