package di

import data.mongo.repository.app_release.AppReleaseRepositoryImpl
import data.mongo.database.MongoDbFactory
import data.mongo.repository.issue_report.IssueReportImpl
import data.mongo.repository.quiz.QuizQuestionImpl
import data.mongo.repository.quiz.QuizTopicImpl
import data.mongo.repository.user.UserActivityImpl
import data.mongo.repository.user.UserRepositoryImpl
import domain.repository.app_release.AppReleaseInfoRepository
import domain.repository.user.UserRepository
import domain.repository.issue_report.IssueReportRepository
import domain.repository.quiz.QuizQuestionRepository
import domain.repository.quiz.QuizTopicRepository
import domain.repository.user.UserActivityRepository
import data.s3.S3ServicesRepositoryImpl
import domain.repository.s3.S3ServicesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val koinModule = module {

    single {MongoDbFactory.create()}
    singleOf(:: QuizQuestionImpl).bind<QuizQuestionRepository>()
    singleOf(:: QuizTopicImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportImpl).bind<IssueReportRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::UserActivityImpl).bind<UserActivityRepository>()
    singleOf(::AppReleaseRepositoryImpl).bind<AppReleaseInfoRepository>()
    singleOf(::S3ServicesRepositoryImpl).bind<S3ServicesRepository>()



//
//    single{ S3Client.create() }



}