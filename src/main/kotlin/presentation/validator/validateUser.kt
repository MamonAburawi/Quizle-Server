package presentation.validator

import domain.model.User
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import presentation.validator.AppValidationRule.createInvalidResult


fun RequestValidationConfig.validateUser() {
    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")

    validate<User> { user ->
        when {
            user.userName.isEmpty() ->
                createInvalidResult(
                    messageEn = "User name is required!",
                    messageAr = "اسم المستخدم مطلوب!",
                )

            user.email.isEmpty() ->
                createInvalidResult(
                    messageEn = "Email is required!",
                    messageAr = "البريد الإلكتروني مطلوب!",
                )

            user.email.isNotEmpty() && !user.email.matches(emailRegex) ->
                createInvalidResult(
                    messageEn = "Invalid email address.",
                    messageAr = "عنوان بريد إلكتروني غير صالح.",
                )

            user.password.isEmpty() || user.password.length < 6 ->
                createInvalidResult(
                    messageEn = "Password must be at least 6 characters long.",
                    messageAr = "يجب أن تتكون كلمة المرور من 6 أحرف على الأقل.",
                )

            else -> ValidationResult.Valid
        }
    }
}

