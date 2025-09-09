package presentation.route.s3

import domain.repository.S3ServicesRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.server.resources.delete
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.deleteImageProfile(
    repository: S3ServicesRepository
) {
    delete<ImageProfile.Delete> { path ->
        val imageUrl = path.imageUrl
        repository.deleteAPhotoByImageUrl(imageUrl = imageUrl)
            .onSuccess {
                call.respondSuccess<Unit>(
                    messageEn = "Image profile deleted successfully.",
                    messageAr = "تم حذف صورة الملف الشخصي بنجاح."
                )
            }
            .onFailure {
                call.respondError(it)
            }
    }
}
