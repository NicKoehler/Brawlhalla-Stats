package com.nickoehler.brawlhalla.core.presentation.util

import android.content.Context
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NOT_FOUND -> R.string.error_not_found
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_server_error
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.BAD_REQUEST -> R.string.error_bad_request
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}