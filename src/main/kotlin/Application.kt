package com

import io.ktor.server.application.*
import presentation.plugin.configureKoin
import presentation.plugin.configureLogging
import presentation.plugin.configureRouting
import presentation.plugin.configureSerialization
import presentation.plugin.configureExceptionHandling
import presentation.plugin.configureValidation

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureRouting()
    configureLogging()
    configureValidation()
    configureExceptionHandling()
    configureSerialization()
}


