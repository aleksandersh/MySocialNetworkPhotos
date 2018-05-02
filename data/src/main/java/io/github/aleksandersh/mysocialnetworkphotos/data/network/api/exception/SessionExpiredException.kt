package io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception

import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.NetworkException

class SessionExpiredException : NetworkException {

    constructor() : super()

    constructor(message: String) : super(message)
}