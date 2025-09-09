package presentation.validator

import domain.model.IssueReport
import presentation.validator.AppValidationRule.createInvalidResult
import io.ktor.server.plugins.requestvalidation.*


fun RequestValidationConfig.validateIssueReport() {

    validate<IssueReport> { issueReport ->
        when {
            // Rule 1: Issue type must not be empty
            issueReport.issueType.isBlank() ->
                createInvalidResult(
                    messageEn = "Issue type must not be empty.",
                    messageAr = "نوع المشكلة يجب ألا يكون فارغًا."
                )

            // Rule 2: Additional comment must be at least 5 characters long if provided
            issueReport.additionalComment != null && issueReport.additionalComment.length < 5 ->
                createInvalidResult(
                    messageEn = "Additional comment must be at least 5 characters long.",
                    messageAr = "التعليق الإضافي يجب أن يكون 5 أحرف على الأقل."
                )

            // Rule 3: User email must be valid if provided
            issueReport.userId.isEmpty() ->
                createInvalidResult(
                    messageEn = "User id must not be empty",
                    messageAr = "معرف المستخدم يجب ان لا يكون فارغا"
                )

            // All validations passed
            else -> ValidationResult.Valid
        }
    }
}
