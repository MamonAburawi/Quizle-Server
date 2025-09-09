package domain.repository.app_release

import com.domain.model.AppReleaseInfo
import domain.util.Result
import data.util.exception.AppException

interface AppReleaseInfoRepository {

    suspend fun getLastReleaseInfo(): Result<AppReleaseInfo, AppException>

    suspend fun insertReleaseInfo(release: AppReleaseInfo): Result<Unit, AppException>

}