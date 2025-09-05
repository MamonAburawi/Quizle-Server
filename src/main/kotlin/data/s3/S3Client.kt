package data.s3

import io.ktor.server.engine.applicationEnvironment
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client


object S3Client {

    fun create(accessKey: String, secretKey: String): S3Client {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)

        return S3Client.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }

}


//
//object S3Client {
//
//    fun create(): S3Client{
//        var accessKey = ""
//        var secrete = ""
//         applicationEnvironment {
//             accessKey = config.property("aws.accessKey").getString()
//             secrete = config.property("aws.secrete").getString()
//         }
//
//        val credit = AwsBasicCredentials.create(accessKey,secrete)
//
//        return S3Client.builder()
//            .region(Region.US_EAST_1)
////            .credentialsProvider(ProfileCredentialsProvider.create())
//            .credentialsProvider(StaticCredentialsProvider.create(credit))
//            .build()
//    }
//
//}