package di

import data.mongo.repository.app_release.AppReleaseRepositoryImpl
import data.mongo.repository.issue_report.IssueReportImpl
import data.mongo.repository.quiz.QuestionImpl
import data.mongo.repository.quiz.TopicImpl
import data.mongo.repository.user.LogEventImpl
import data.mongo.repository.user.UserRepositoryImpl
import domain.repository.AppReleaseInfoRepository
import domain.repository.UserRepository
import domain.repository.IssueReportRepository
import domain.repository.QuizQuestionRepository
import domain.repository.QuizTopicRepository
import domain.repository.LogEventRepository
import data.s3.S3ServiceRepositoryImpl
import domain.repository.S3ServicesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val koinModule = module {

//    single {
//
//        val context =
//        val mogo = context.environment.config.config("mongo")
//        val stringConnection = mogo.property("url").getString()
//
//        MongoDbFactory.create(get())
//    }
    singleOf(:: QuestionImpl).bind<QuizQuestionRepository>()
    singleOf(:: TopicImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportImpl).bind<IssueReportRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::LogEventImpl).bind<LogEventRepository>()
    singleOf(::AppReleaseRepositoryImpl).bind<AppReleaseInfoRepository>()
    singleOf(::S3ServiceRepositoryImpl).bind<S3ServicesRepository>()



}