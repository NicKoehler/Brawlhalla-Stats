package com.nickoehler.brawlhalla.core.data.networking

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }

        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)

        404 -> Result.Error(NetworkError.NOT_FOUND)

        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)

        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)

        else -> Result.Error(NetworkError.UNKNOWN)
    }
}