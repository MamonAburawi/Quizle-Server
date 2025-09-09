package com.di


import data.mongo.database.MongoDbClient
import io.ktor.server.application.Application
import org.koin.dsl.module

fun Application.mongoModule() = module {

    single {
        val mogo = environment.config.config("mongo")
        val stringConnection = mogo.property("url").getString()

        MongoDbClient.create(stringConnection)
    }

}

