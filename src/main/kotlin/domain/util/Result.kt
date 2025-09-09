package domain.util

import data.util.exception.AppException


sealed interface Result<out D, out E: AppException> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Failure<out E: AppException>(val error: E): Result<Nothing, E>
}


inline fun <T,E: AppException> Result<T,E>.onSuccess(action: (T) -> Unit): Result<T,E>{
    if (this is Result.Success) action(data)
    return this
}

inline fun <T,E: AppException> Result<T,E>.onFailure(action: (E) -> Unit): Result<T,E>{
    if(this is Result.Failure) action(error)
    return this
}

