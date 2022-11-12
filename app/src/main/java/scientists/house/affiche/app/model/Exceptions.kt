package scientists.house.affiche.app.model

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ConnectionException(cause: Throwable) : AppException(cause = cause)

open class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

internal inline fun <T> wrapBackendExceptions(block: () -> T): T {
    try {
        return block.invoke()
    } catch (e: BackendException) {
        throw e
    }
}
