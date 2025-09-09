package presentation.route.user

import io.ktor.resources.Resource


@Resource("/user")
class UserRoutePath {

    @Resource("{userId}")
    data class ById(
        val parent: UserRoutePath = UserRoutePath(),
        val userId: String
    )

    @Resource("login")
    data class Login(
        val parent: UserRoutePath = UserRoutePath(),
        val email: String, // parameter
        val password: String, // parameter
        val tokenExp: Long? = null // parameter: you have to add init value "null"
    )


    @Resource("register")
    data class Register(
        val parent: UserRoutePath = UserRoutePath(),
    )


    @Resource("update")
    data class Update(
        val parent: UserRoutePath = UserRoutePath()
    )

    @Resource("force_logout")
    data class TerminateSession(
        val parent: UserRoutePath = UserRoutePath(),
        val userId: String
    )

    @Resource("activity")
    data class UserActivity(
        val parent: UserRoutePath = UserRoutePath(),
        val userId: String? = null,
        val userName: String? = null,
        val createdAt: Long? = null,
        val action: String? = null,
        val limit: Int? = null
    )


    @Resource("add_activity")
    data class AddActivity(
        val parent: UserRoutePath = UserRoutePath(),
        val email: String,
        val action: String
    )







}