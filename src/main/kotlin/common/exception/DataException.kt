package common.exception



// Data-related exceptions
sealed class DataException(messageEn: String, messageAr: String) : AppException(messageEn, messageAr) {

    class MissingRequiredParameterException: DataException(
        messageEn = "A required parameter is missing from the request.",
        messageAr = "أحد البيانات المطلوبة مفقود في الطلب."
    )
    class InvalidDataProvidedException: DataException(
        messageEn = "The data provided is invalid.",
        messageAr = "البيانات المقدمة غير صالحة."
    )

    class UserNotFoundException : DataException(
        messageEn = "User data not found.",
        messageAr = "بيانات المستخدم غير موجودة."
    )
    class AppReleaseNotFoundException: DataException(
        messageEn = "No app release info found",
        messageAr = "لم يتم العثور على معلومات حول إصدار التطبيق"
    )

    class ReportIssueNotFoundException: DataException(
        messageEn = "The specified report issue was not found.",
        messageAr = "لم يتم العثور على مشكلة التقرير المحددة."
    )

    class QuestionsNotFoundException: DataException(
        messageEn = "The requested questions could not be found.",
        messageAr = "لم يتم العثور على الأسئلة المطلوبة."
    )

    class QuestionNotFoundException: DataException(
        messageEn = "The requested question could not be found.",
        messageAr = "لم يتم العثور على السؤال المطلوب."
    )

   class TopicsNotFoundException: DataException(
       messageEn = "The requested topics could not be found.",
       messageAr = "لم يتم العثور على المواضيع المطلوبة."
   )

    class TopicNotFoundException: DataException(
        messageEn = "The requested topic could not be found.",
        messageAr = "لم يتم العثور على الموضوع المطلوب."
    )

    class UserActivitiesNotFoundException: DataException(
        messageEn = "User activities could not be found.",
        messageAr = "لم يتم العثور على أنشطة المستخدم."
    )

    class InsertImageProfileException: DataException(
        messageEn = "Failed to insert the image",
        messageAr = "فشل في إدراج الصورة."
    )

}