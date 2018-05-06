package io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception

class ResponseParsingException :
    NetworkException {

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)
}