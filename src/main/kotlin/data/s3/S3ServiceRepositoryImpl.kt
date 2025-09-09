package data.s3

import com.common.getFileNameFromUrl
import common.constant.AWSConstants
import domain.repository.S3ServicesRepository
import domain.util.Result
import common.exception.AppException
import common.exception.DataException
import common.exception.DatabaseException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest


class S3ServiceRepositoryImpl(
    private val s3Client: S3Client
): S3ServicesRepository {

    override suspend fun insertAPhotoToS3(fileName: String, photoByteArray: ByteArray?): Result<String, AppException> {
        return try {
            val path = "${AWSConstants.FOLDER_NAME}/$fileName"
            val putObjectRequest = PutObjectRequest
                .builder()
                .bucket(AWSConstants.BUCKET_NAME)
                .key(path)
                .build()

            // Basic validation of the received data
            if (fileName.isEmpty() || photoByteArray == null) {
                Result.Failure(DataException.InvalidDataProvidedException())
            }else {
                val fullImagePath = "${AWSConstants.BASE_IMAGE_PROFILE_PATH}/$fileName"
                val putResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(photoByteArray))
                if (putResponse.sdkHttpResponse().isSuccessful) {
                    Result.Success(fullImagePath)
                } else {
                    Result.Failure(DataException.InsertImageProfileException())
                }
            }

        }catch (ex: Exception){
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


    // you can use this to download image
    override suspend fun getAPhotoByImageUrl(imageUrl: String): Result<ByteArray, AppException> {
        return try {
            val fileName = imageUrl.getFileNameFromUrl()
            val path = "${AWSConstants.FOLDER_NAME}/$fileName"
            val getObjectRequest = GetObjectRequest
                .builder()
                .bucket(AWSConstants.BUCKET_NAME)
                .key(path)
                .build()

            val byteArray = s3Client.getObjectAsBytes(getObjectRequest).asByteArray()
            Result.Success(byteArray)
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
        }
    }


    override suspend fun deleteAPhotoByImageUrl(imageUrl: String): Result<Unit, AppException> {
        return try {
            val fileName = imageUrl.getFileNameFromUrl()
            val path = "${AWSConstants.FOLDER_NAME}/$fileName"
            val deleteObjectRequest = DeleteObjectRequest
                .builder()
                .bucket(AWSConstants.BUCKET_NAME)
                .key(path)
                .build()

            s3Client.deleteObject(deleteObjectRequest)
            Result.Success(Unit)

        }catch (ex: Exception){
            ex.printStackTrace()
            Result.Failure(DatabaseException.UnknowErrorException())
        }

    }
}

