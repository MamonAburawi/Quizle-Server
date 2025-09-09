package presentation.route.s3


import domain.repository.S3ServicesRepository
import domain.util.onFailure
import domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.request.receiveMultipart
import io.ktor.server.resources.*
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import domain.util.response.respondError
import domain.util.response.respondSuccess

fun Route.insertImageProfile(
    repository: S3ServicesRepository
) {

    post<ImageProfile.AddImage> { path ->

        var fileName = ""
        var fileBytes: ByteArray? = null
        val multipart = call.receiveMultipart()

        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                fileName = part.originalFileName as String
                fileBytes = part.streamProvider().readBytes()
            }
            part.dispose()
        }

        repository.insertAPhotoToS3(fileName = fileName, photoByteArray = fileBytes)
            .onSuccess { imageUrl ->
              call.respondSuccess(
                  data = imageUrl,
                  statusCode = HttpStatusCode.Created
              )
            }
            .onFailure {
                call.respondError(it)
            }


    }
}
