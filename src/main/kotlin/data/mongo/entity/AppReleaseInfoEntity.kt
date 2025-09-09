package com.data.mongo.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class AppReleaseInfoEntity(
    @BsonId
    val _id: String = ObjectId().toString(),
    val versionName: String,
    val versionCode: Int,
    val releaseDate: Long?,
    val releaseNoteAr: String,
    val releaseNoteEn: String,
    val downloadLink: String
)