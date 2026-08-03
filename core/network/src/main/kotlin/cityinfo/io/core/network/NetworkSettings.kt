package cityinfo.io.core.network

import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

private const val CONNECTION_TIMEOUT_MIN = 40_000L

fun HttpClientConfig<*>.installJson() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            prettyPrint = BuildConfig.isDebug
            ignoreUnknownKeys = true
            explicitNulls = true
            encodeDefaults = true
        })
    }
}

fun HttpClientConfig<*>.installDefaultRequest(
    baseUrl: String,
) {
    defaultRequest {
        url {
            protocol = URLProtocol.HTTP
            host = baseUrl
        }
        header(
            key = "Content-Type",
            value = "application/json",
        )
    }
    installHttpResponseValidator()
}

fun HttpClientConfig<*>.installHttpResponseValidator() {
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            val clientException =
                exception as? ResponseException ?: return@handleResponseExceptionWithRequest
            val response = clientException.response
            val jsonError = runCatching { response.body<JsonObject>().toString() }.getOrDefault("")
            val status = response.status.value
            throw CityKtorError(status, jsonError)
        }
    }
}

fun HttpClientConfig<*>.installTimeOut() {
    install(HttpTimeout) {
        requestTimeoutMillis = CONNECTION_TIMEOUT_MIN
        connectTimeoutMillis = CONNECTION_TIMEOUT_MIN
        socketTimeoutMillis = CONNECTION_TIMEOUT_MIN
    }
}