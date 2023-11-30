package gustavo.projects.moviemanager.network.util

import gustavo.projects.moviemanager.network.models.ApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response

sealed class ApiResult<T> {
    class Success<T>(val data: T, val code: Int) : ApiResult<T>()
    class Error<T>(val data: ApiError, val code: Int? = null) : ApiResult<T>()
}

fun <T> ApiResult<T>.asFlow(): Flow<Either<T, ApiError>> {
    val result = this
    return flow {
        when (result) {
            is ApiResult.Success -> emit(left(result.data))
            is ApiResult.Error -> emit(right(result.data))
        }
    }
}

suspend fun <T : Any> toApiResult(
    execute: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.Success(body, response.code())
        } else {
            ApiResult.Error(data = ApiError(code = response.code().toString()))
        }
    } catch (e: HttpException) {
        ApiResult.Error(
            data = ApiError(code = e.code().toString(), exception = e)
        )
    } catch (e: Throwable) {
        ApiResult.Error(data = ApiError(exception = e))
    }
}
