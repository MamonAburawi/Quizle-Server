package presentation.route.s3

import io.ktor.resources.Resource


@Resource("/user")
class ImageProfile {

    @Resource("/addImageProfile")
    data class AddImage(
        val parent: ImageProfile = ImageProfile(),
    )

    @Resource("/imageProfile")
    data class GetImage(
        val parent: ImageProfile = ImageProfile(),
        val imageUrl: String
    )

    @Resource("/deleteImageProfile")
    data class Delete(
        val parent: ImageProfile = ImageProfile(),
        val imageUrl: String
    )


}



