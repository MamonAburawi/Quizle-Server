package presentation.route.s3

import domain.repository.s3.S3ServicesRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess


fun Route.getPhotoByImageUrl(
    repository: S3ServicesRepository
) {
    get<ImageProfile.GetImage> { path ->

        val imageUrl = path.imageUrl

        repository.getAPhotoByImageUrl(imageUrl = imageUrl)
            .onSuccess { byteArray ->
                call.respondSuccess(data = byteArray)
            }
            .onFailure {
                call.respondError(it)
            }

    }
}
