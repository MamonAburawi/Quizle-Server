package data.util.exception



// User-related exceptions
sealed class AuthException(messageEn: String, messageAr: String) : AppException(messageEn, messageAr) {
    class InvalidCredentialsException : AuthException(
        messageEn = "Invalid email or password.",
        messageAr = "البريد الإلكتروني أو كلمة المرور غير صحيحة."
    )
    class UserAlreadyExistsException : AuthException(
        messageEn = "User with this email already exists.",
        messageAr = "يوجد مستخدم بنفس البريد الإلكتروني بالفعل."
    )

    class UserNotExistsException : AuthException(
        messageEn = "User with this email is not exists.",
        messageAr = "هذا المستخدم غير موجود."
    )


}
