package io.github.aleksandersh.mysocialnetworkphotos.data.network.exception

open class NetworkException : RuntimeException {

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)
}