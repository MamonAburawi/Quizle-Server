package com.presentation.route.app_release

import io.ktor.resources.Resource


@Resource("/release")
class AppReleasePath {


    @Resource("last")
    data class Last(
        val parent: AppReleasePath = AppReleasePath(),
    )

}