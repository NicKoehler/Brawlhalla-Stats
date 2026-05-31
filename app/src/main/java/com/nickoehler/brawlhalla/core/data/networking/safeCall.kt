package com.nickoehler.brawlhalla.core.data.networking

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import java.nio.channels.UnresolvedAddressException


suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (_: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (_: JsonConvertException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (_: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response)
}