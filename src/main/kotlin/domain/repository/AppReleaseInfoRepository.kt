package domain.repository

import com.domain.model.AppReleaseInfo
import common.exception.AppException
import domain.util.Result

interface AppReleaseInfoRepository {

    suspend fun getLastReleaseInfo(): Result<AppReleaseInfo, AppException>

    suspend fun insertReleaseInfo(release: AppReleaseInfo): Result<Unit, AppException>

}