package common.exception


// General server exceptions
sealed class ServerException(messageEn: String, messageAr: String) : AppException(messageEn, messageAr) {

    class NetworkUnavailableException : ServerException(
        messageEn = "Network is not available.",
        messageAr = "الشبكة غير متوفرة."
    )


}


