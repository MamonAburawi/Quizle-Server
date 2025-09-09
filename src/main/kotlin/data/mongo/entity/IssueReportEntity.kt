package com.data.mongo.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class IssueReportEntity(
    @BsonId
    val _id: String? = ObjectId().toString(),
    val questionId: String,
    val issueType: String,
    val additionalComment: String?,
    val userId: String,
    val createdAt: Long,
    val isResolved: Boolean?
)