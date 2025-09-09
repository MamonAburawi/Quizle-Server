package presentation.plugin

import presentation.validator.validateUser
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import presentation.validator.validateIssueReport
import presentation.validator.validateQuizQuestion
import presentation.validator.validateQuizTopic


fun Application.configureValidation(){
    install(RequestValidation){

        validateQuizQuestion()
        validateQuizTopic()
        validateIssueReport()
        validateUser()
    }
}