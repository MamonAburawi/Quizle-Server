package data.util

import software.amazon.awssdk.regions.Region

object AWSConstants {

    const val BUCKET_NAME = "quizle"

    const val FOLDER_NAME = "ImageProfile"

    val BASE_IMAGE_PROFILE_PATH = "https://${BUCKET_NAME}.s3.${Region.US_EAST_1}.amazonaws.com/${FOLDER_NAME}"


}