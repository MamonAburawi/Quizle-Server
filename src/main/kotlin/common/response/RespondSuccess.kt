package domain.util.response

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.acceptLanguage
import io.ktor.server.response.respond

// Generic extension function to handle all success responses dynamically
suspend fun <T> ApplicationCall.respondSuccess(
    data: T? = null,
    messageEn: String? = null,
    messageAr: String? = null,
    statusCode: HttpStatusCode = HttpStatusCode.OK
) {
    if (data != null) {
        // If data is provided, respond with the data object
        this.respond(status = statusCode, message = data)
    } else if (messageEn != null && messageAr != null) {
        // If no data but messages are provided, respond with a localized message
        val lang = this.request.acceptLanguage()
        val localizedMessage = if (lang?.startsWith("ar") == true) {
            messageAr
        } else {
            messageEn
        }
        this.respond(status = statusCode, message = localizedMessage)
    } else {
        // Fallback for cases with no data or message
        this.respond(HttpStatusCode.NoContent)
    }
}