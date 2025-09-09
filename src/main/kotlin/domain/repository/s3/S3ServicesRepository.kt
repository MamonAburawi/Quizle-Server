package domain.repository.s3

import domain.util.Result
import data.util.exception.AppException

interface S3ServicesRepository {

    suspend fun insertAPhotoToS3(fileName: String, photoByteArray: ByteArray?): Result<String, AppException>

    suspend fun getAPhotoByImageUrl(imageUrl: String):  Result<ByteArray, AppException>

    suspend fun deleteAPhotoByImageUrl(imageUrl: String): Result<Unit, AppException>

}