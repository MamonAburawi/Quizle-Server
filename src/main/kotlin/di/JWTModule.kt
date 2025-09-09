package di

import com.presentation.plugin.JWTConfig
import com.presentation.plugin.JWTService
import io.ktor.server.application.Application
import org.koin.dsl.module

fun Application.jwtModule() = module {

    // Single instance of JWTConfig, retrieved from Ktor's environment configuration
    single {

        val jwt = environment.config.config("jwt")
        val realm = jwt.property("realm").getString()
        val issuer = jwt.property("issuer").getString()
        val audience = jwt.property("audience").getString()
        val secret = jwt.property("secret").getString() // Corrected property name
        val tokenExpiry = jwt.property("expiry").getString().toLong()

        JWTConfig(
            realm = realm,
            issuer = issuer,
            audience = audience,
            tokenExpiry = tokenExpiry,
            secret = secret
        )
    }

    // Single instance of JWTService, which depends on JWTConfig
    single { JWTService(jwtConfig = get()) } // Koin will automatically inject JWTConfig here
}

