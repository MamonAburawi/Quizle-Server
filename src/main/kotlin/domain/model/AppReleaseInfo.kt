package com.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AppReleaseInfo(
    @SerialName("id") val id: String? = null,
    @SerialName("version_name") val versionName: String,
    @SerialName("version_code") val versionCode: Int,
    @SerialName("release_date") val releaseDate: Long? = null,
    @SerialName("release_note_Ar") val releaseNoteAr: String,
    @SerialName("release_note_En") val releaseNoteEn: String,
    @SerialName("download_link") val downloadLink: String
)

