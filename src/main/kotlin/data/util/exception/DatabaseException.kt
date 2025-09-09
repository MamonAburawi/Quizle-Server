package data.util.exception


sealed class DatabaseException(messageEn: String, messageAr: String) : AppException(messageEn, messageAr) {

    class UnknowErrorException : DatabaseException(
        messageEn = "An unexpected error occurred.",
        messageAr = "حدث خطأ غير متوقع"
    )



}


