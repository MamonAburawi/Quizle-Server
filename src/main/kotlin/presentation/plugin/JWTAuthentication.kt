package com.presentation.plugin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.model.user.User
import data.util.MongoDBConstants
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respondText
import java.util.Date
import kotlin.time.Duration.Companion.days


fun Application.configureJWTAuthentication(jwtService: JWTService) {
    install(Authentication) {
        val config = jwtService.jwtConfig
        jwt(MongoDBConstants.JWT_CONFIGURATION_NAME) {
            realm = config.realm

            val jwtVerifier = JWT
                .require(Algorithm.HMAC256(config.secret))
                .withIssuer(config.issuer)
                .withAudience(config.audience)
                .build()
            verifier(jwtVerifier)

            validate {jwtCredential ->
                val userEmail = jwtCredential.payload.getClaim(User::email.name).asString()
                if (!userEmail.isNullOrEmpty()){
                    JWTPrincipal(jwtCredential.payload)
                }

            }

            challenge { _,_ ->
                call.respondText(
                    text = "Token is not valid or expired",
                    status = HttpStatusCode.Unauthorized
                )

            }
        }
    }
}


class JWTService(val jwtConfig: JWTConfig) {

    // you can bind username or email or any field to jwt token
    fun generateToken(
        email: String,
        createdAt: Long = System.currentTimeMillis(),
        expAt: Long? = null,
    ): String {
        // token will be expired after one day in default.
        val tokenExpiry = expAt ?: (createdAt + 1.days.inWholeMilliseconds)
        return try {
            val algorithm = Algorithm.HMAC256(jwtConfig.secret)
            JWT.create()
                .withIssuer(jwtConfig.issuer)
                .withAudience(jwtConfig.audience)
                .withClaim(User::email.name, email)
                .withExpiresAt(Date( tokenExpiry))
                .sign(algorithm)
        } catch (e: Exception) {
            println("Error generating token: ${e.message}")
            throw RuntimeException("Failed to generate JWT token", e)
        }
    }
}


data class JWTConfig(
    val secret: String,
    val realm: String,
    val issuer: String,
    val audience: String,
    val tokenExpiry: Long
)