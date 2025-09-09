package domain.repository

import common.exception.AppException
import domain.util.Result

interface S3ServicesRepository {

    suspend fun insertAPhotoToS3(fileName: String, photoByteArray: ByteArray?): Result<String, AppException>

    suspend fun getAPhotoByImageUrl(imageUrl: String): Result<ByteArray, AppException>

    suspend fun deleteAPhotoByImageUrl(imageUrl: String): Result<Unit, AppException>

}