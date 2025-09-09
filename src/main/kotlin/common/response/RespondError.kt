package domain.util.response

import domain.model.ErrorResponse
import common.exception.AppException
import common.exception.AuthException
import common.exception.DataException
import common.exception.DatabaseException
import common.exception.ServerException
import io.ktor.server.response.respond
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.acceptLanguage


suspend fun ApplicationCall.respondError(cause: AppException) {
    // Determine the language from the request headers
    val lang = this.request.acceptLanguage()
    val message = if (lang?.startsWith("ar") == true) cause.messageAr else cause.messageEn

    // Map the exception type to an appropriate HTTP status code
    val statusCode = when (cause) {
        is DataException -> HttpStatusCode.NotFound
        is AuthException -> HttpStatusCode.Unauthorized
        is ServerException -> HttpStatusCode.ServiceUnavailable
        is DatabaseException -> HttpStatusCode.InternalServerError
    }

    // Respond with the localized error message and status code
    this.respond(
        status = statusCode,
        message = ErrorResponse(code = statusCode.value, message = message)
    )
}