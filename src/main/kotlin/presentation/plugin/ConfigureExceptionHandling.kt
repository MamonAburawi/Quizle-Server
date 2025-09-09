package presentation.plugin

import domain.model.ErrorResponse
import common.exception.AppException
import common.exception.AuthException
import common.exception.DataException
import common.exception.DatabaseException
import common.exception.ServerException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.acceptLanguage
import io.ktor.server.response.respond

fun Application.configureExceptionHandling(){
    install(StatusPages){
        exception<RequestValidationException>{call, cause ->
            call.respond(
                message = cause.reasons.joinToString(),
                status = HttpStatusCode.BadRequest
            )
        }

        exception<AppException> { call, cause ->
            val lang = call.request.acceptLanguage()
            val message = if (lang?.startsWith("ar") == true) cause.messageAr else cause.messageEn

            // Map specific exceptions to HTTP status codes
            val statusCode = when (cause) {
                is DataException -> HttpStatusCode.NotFound
                is AuthException -> HttpStatusCode.Unauthorized
                is ServerException -> HttpStatusCode.ServiceUnavailable
                is DatabaseException -> HttpStatusCode.InternalServerError
            }

            call.respond(
                status = statusCode,
                message = ErrorResponse(code = statusCode.value, message = message)
            )
        }

        // Fallback for any other exceptions (like NullPointerException)
        exception<Throwable> { call, cause ->
            val lang = call.request.acceptLanguage()
            val message = if (lang?.startsWith("ar") == true) "حدث خطأ غير متوقع" else "An unexpected error occurred."
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(code = HttpStatusCode.InternalServerError.value, message = message)
            )
        }


    }
}


