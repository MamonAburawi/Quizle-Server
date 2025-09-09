package data.util.exception


// The root of your custom exception hierarchy
sealed class AppException(val messageEn: String, val messageAr: String) : RuntimeException()