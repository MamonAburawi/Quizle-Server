package domain.repository

import domain.model.User
import domain.util.Result
import common.exception.AppException

interface UserRepository {

    suspend fun login(email: String, password: String, tokenExp: Long?): Result<User, AppException>

    suspend fun register(user: User): Result<User, AppException>

    suspend fun getUserById(id: String): Result<User, AppException>

    suspend fun updateUser(user: User): Result<Unit, AppException>

    suspend fun terminateSession(userId: String): Result<Unit, AppException>


}