package presentation.plugin

import domain.repository.AppReleaseInfoRepository
import domain.repository.UserRepository
import com.presentation.plugin.JWTService
import com.presentation.plugin.configureJWTAuthentication
import presentation.route.quiz_question.getRandomQuestions
import presentation.route.quiz_question.insertQuestionBulk
import presentation.route.user.getUserById
import presentation.route.user.login
import presentation.route.user.register
import domain.repository.IssueReportRepository
import domain.repository.QuizQuestionRepository
import domain.repository.QuizTopicRepository
import domain.repository.LogEventRepository
import presentation.route.app_release.getLastAppRelease
import presentation.route.app_release.insetAppRelease
import presentation.route.quiz_topic.getQuizTopicsUsedInQuestions
import presentation.route.quiz_topic.searchQuizTopics
import com.presentation.route.user.getAllUserActivity
import presentation.route.user.logEvent
import presentation.route.user.terminateSession
import presentation.route.user.updateUser
import domain.repository.S3ServicesRepository
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import presentation.route.issue_report.*
import presentation.route.quiz_question.*
import presentation.route.quiz_topic.*
import presentation.route.root
import presentation.route.s3.deleteImageProfile
import presentation.route.s3.getPhotoByImageUrl
import presentation.route.s3.insertImageProfile


fun Application.configureRouting() {
    install(Resources)

//    val jwt = environment.config.config("ktor.jwt")
//
//    val realm = jwt.property("realm").getString()
//    val issuer = jwt.property("issuer").getString()
//    val audience = jwt.property("audience").getString()
//    val secrete = jwt.property("secrete").getString()
//    val tokenExpiry = jwt.property("expiry").getString().toLong()
//
//    val config = JWTConfig(
//        realm = realm,
//        issuer = issuer,
//        audience = audience,
//        secret = secrete,
//        tokenExpiry = tokenExpiry
//    )
//


    val quizQuestionImpl: QuizQuestionRepository by inject()
    val quizTopicImpl: QuizTopicRepository by inject()
    val issueReportImpl: IssueReportRepository by inject()
    val userRepositoryImpl: UserRepository by inject()
    val logEventRepositoryImpl: LogEventRepository by inject()
    val appReleaseImpl: AppReleaseInfoRepository by inject()
    val s3ServicesImpl: S3ServicesRepository by inject()

    val jwtService: JWTService by inject()

    routing {

        root()

        configureJWTAuthentication(jwtService = jwtService)

        // quiz question
        getAllQuizQuestions(quizQuestionImpl )
        upsertQuizQuestions(quizQuestionImpl)
        deleteQuizQuestionById(quizQuestionImpl)
        getQuizQuestionById(quizQuestionImpl)
        getRandomQuestions(quizQuestionImpl)
        insertQuestionBulk(quizQuestionImpl)

        // quiz topic
        getAllQuizTopic(quizTopicImpl)
        getQuizTopicById(quizTopicImpl)
        deleteQuizTopicById(quizTopicImpl)
        upsertQuizTopic(quizTopicImpl)
        getQuizTopicsUsedInQuestions(quizTopicImpl)
        searchQuizTopics(quizTopicImpl)


        // issue report
        getAllIssueReport(issueReportImpl)
        deleteIssueReportById(issueReportImpl)
        insertIssueReport(issueReportImpl)

        // app release
        insetAppRelease(appReleaseImpl)
        getLastAppRelease(appReleaseImpl)


        // user
        getUserById(userRepositoryImpl)
        login(userRepositoryImpl)
        register(userRepositoryImpl)
        terminateSession(userRepositoryImpl)
        updateUser(userRepositoryImpl)
        getAllUserActivity(logEventRepositoryImpl)
        logEvent(logEventRepositoryImpl)


        // s3Services
        insertImageProfile(s3ServicesImpl)
        getPhotoByImageUrl(s3ServicesImpl)
        deleteImageProfile(s3ServicesImpl)


        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )

    }
}

