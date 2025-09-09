package data.mongo.repository.issue_report

import com.data.mongo.entity.IssueReportEntity
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import data.mongo.mapper.toIssueReport
import data.mongo.mapper.toIssueReportEntity
import data.util.MongoDBConstants
import com.domain.model.issue_report.IssueReport
import domain.repository.issue_report.IssueReportRepository
import domain.util.Result
import data.util.exception.AppException
import data.util.exception.DataException
import data.util.exception.DatabaseException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class IssueReportImpl(
    mongoDb: MongoDatabase
): IssueReportRepository {
    val issueReportCollection = mongoDb.getCollection<IssueReportEntity>(MongoDBConstants.ISSUE_REPORT_COLLECTION)

    override suspend fun getAllIssueReport(): Result<List<IssueReport>, AppException> {
       return try {
           val reports = issueReportCollection
               .find()
               .map { it.toIssueReport() }
               .toList()

           if (reports.isNotEmpty()){
               Result.Success(reports)
           }else {
               Result.Failure(DataException.ReportIssueNotFoundException())
           }
       }catch (ex: Exception){
           ex.printStackTrace() // Use proper logging in production
           Result.Failure(DatabaseException.UnknowErrorException())
//           Result.Failure(DataError.Database(
//               messageEn = ex.localizedMessage
//           ))
       }
    }

    override suspend fun deleteIssueReportById(id: String?): Result<Unit, AppException> {
        if (id.isNullOrBlank()){
            Result.Failure(DataException.ReportIssueNotFoundException())
        }
        return try {
            val filter = Filters.eq(IssueReportEntity::_id.name, id)
            val isExist = issueReportCollection.find(filter).firstOrNull() != null
            if (isExist){
                val isDeleted = issueReportCollection.deleteOne(filter).wasAcknowledged()
                if (isDeleted){
                    Result.Success(Unit)
                }else {
                    Result.Failure(DatabaseException.UnknowErrorException())
                }
            }else{
                Result.Failure(DataException.ReportIssueNotFoundException())
            }
        }catch (ex: Exception){
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
//            Result.Failure(DataError.Database(
//                messageEn = ex.localizedMessage
//            ))
        }
    }

    override suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit, AppException> {
        return try {
            val isInserted = issueReportCollection.insertOne(issueReport.toIssueReportEntity()).wasAcknowledged()
            if (isInserted){
                Result.Success(Unit)
            }else {
                Result.Failure(DatabaseException.UnknowErrorException())
            }
        }catch (ex: Exception){
            ex.printStackTrace() // Use proper logging in production
            Result.Failure(DatabaseException.UnknowErrorException())
//            Result.Failure(DataError.Database(
//                messageEn = ex.localizedMessage
//            ))
        }
    }

}