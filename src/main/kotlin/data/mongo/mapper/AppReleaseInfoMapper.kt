package com.data.mapper

import com.data.entity.app_release.AppReleaseInfoEntity
import com.domain.model.AppReleaseInfo


fun AppReleaseInfoEntity.toAppReleaseInfo(): AppReleaseInfo {
    return AppReleaseInfo(
        id = _id,
        versionName = versionName,
        versionCode = versionCode,
        releaseDate = releaseDate,
        releaseNoteAr = releaseNoteAr,
        releaseNoteEn = releaseNoteEn,
        downloadLink = downloadLink
    )
}


fun AppReleaseInfo.toAppReleaseInfoEntity(): AppReleaseInfoEntity {
    return AppReleaseInfoEntity(
        versionName = versionName,
        versionCode = versionCode,
        releaseDate = releaseDate,
        releaseNoteAr = releaseNoteAr,
        releaseNoteEn = releaseNoteEn,
        downloadLink = downloadLink
    )
}