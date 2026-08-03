package cityinfo.io.core.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ExceptionType {
    data object NetworkTimeout : ExceptionType() // TimeOut
    data object General : ExceptionType()
    data object UnknownHost : ExceptionType() // Internet
    data object BadRequest : ExceptionType() // 400 HTTP ERROR
    data object NotAuthorizedException : ExceptionType() // 401 HTTP ERROR
    data object Forbidden : ExceptionType() // 403 HTTP ERROR
    data object NotFound : ExceptionType() // 404 HTTP ERROR
    data object InternalServerError : ExceptionType() // 500 HTTP ERROR
    data object BadGateway : ExceptionType() // 502 HTTP ERROR
    data object ServiceUnavailable : ExceptionType() // 503 HTTP ERROR
    data object ValidationError : ExceptionType() // 422 HTPP ERROR
}

val ExceptionType?.isError
    get() = this != null

fun Throwable.toExceptionType(): ExceptionType = when (this) {
    is CityKtorError -> this.code.handleErrorCode
    is SocketTimeoutException -> ExceptionType.NetworkTimeout
    is UnknownHostException -> ExceptionType.UnknownHost
    else -> {
        when {
            this.message?.contains("400") == true -> ExceptionType.BadRequest
            this.message?.contains("401") == true -> ExceptionType.NotAuthorizedException
            this.message?.contains("403") == true -> ExceptionType.Forbidden
            this.message?.contains("404") == true -> ExceptionType.NotFound
            this.message?.contains("422") == true -> ExceptionType.ValidationError
            this.message?.contains("500") == true -> ExceptionType.InternalServerError
            this.message?.contains("502") == true -> ExceptionType.BadGateway
            this.message?.contains("503") == true -> ExceptionType.ServiceUnavailable
            else -> ExceptionType.General
        }
    }
}

val Int.handleErrorCode: ExceptionType
    get() = when (this) {
        400 -> ExceptionType.BadRequest
        401 -> ExceptionType.NotAuthorizedException
        403 -> ExceptionType.Forbidden
        404 -> ExceptionType.NotFound
        422 -> ExceptionType.ValidationError
        500 -> ExceptionType.InternalServerError
        502 -> ExceptionType.BadGateway
        503 -> ExceptionType.ServiceUnavailable
        else -> ExceptionType.General
    }

data class CityKtorError(val code: Int, val json: String) : Throwable(json) {
    val rawJson
        get() = runCatching { Json.decodeFromString<JsonObject>(json) }.getOrElse { JsonObject(emptyMap()) }
}