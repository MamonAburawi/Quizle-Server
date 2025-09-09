package di

import data.mongo.repository.app_release.AppReleaseRepositoryImpl
import data.mongo.database.MongoDbFactory
import data.mongo.repository.issue_report.IssueReportImpl
import data.mongo.repository.quiz.QuestionImpl
import data.mongo.repository.quiz.TopicImpl
import data.mongo.repository.user.LogEventImpl
import data.mongo.repository.user.UserRepositoryImpl
import domain.repository.app_release.AppReleaseInfoRepository
import domain.repository.user.UserRepository
import domain.repository.issue_report.IssueReportRepository
import domain.repository.quiz.QuizQuestionRepository
import domain.repository.quiz.QuizTopicRepository
import domain.repository.user.LogEventRepository
import data.s3.S3ServiceRepositoryImpl
import domain.repository.s3.S3ServicesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val koinModule = module {

    single {MongoDbFactory.create()}
    singleOf(:: QuestionImpl).bind<QuizQuestionRepository>()
    singleOf(:: TopicImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportImpl).bind<IssueReportRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::LogEventImpl).bind<LogEventRepository>()
    singleOf(::AppReleaseRepositoryImpl).bind<AppReleaseInfoRepository>()
    singleOf(::S3ServiceRepositoryImpl).bind<S3ServicesRepository>()



}