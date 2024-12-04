package com.nickoehler.brawlhalla.core.data.networking

import com.nickoehler.brawlhalla.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    level = LogLevel.INFO
                    logger = object : Logger {
                        override fun log(message: String) {
                            println(
                                message.replace(
                                    BuildConfig.API_KEY,
                                    "*".repeat(BuildConfig.API_KEY.length)
                                )
                            )
                        }
                    }
                }
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(
                    ContentType.Application.Json
                )
                url {
                    protocol = URLProtocol.HTTPS
                    host = BuildConfig.API_HOST
                    parameters.append("api_key", BuildConfig.API_KEY)
                }
            }
        }
    }
}