package di

import data.s3.S3Client
import data.util.AWSConstants
import data.util.MongoDBConstants
import io.ktor.server.application.Application
import org.koin.dsl.module



fun Application.awsModule() = module {
    single {
        val aws = environment.config.config("aws")
        val accessKey = aws.property("accessKey").getString()
        val secretAccessKey = aws.property("secret").getString()
        S3Client.create(accessKey, secretAccessKey)
    }
}

