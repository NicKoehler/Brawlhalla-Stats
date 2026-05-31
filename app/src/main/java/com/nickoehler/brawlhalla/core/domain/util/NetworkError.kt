package com.nickoehler.brawlhalla.core.domain.util

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NOT_FOUND,
    BAD_REQUEST,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}

