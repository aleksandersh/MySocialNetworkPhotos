package io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception

import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.NetworkException

open class ApiException : NetworkException {

    val code: Int

    constructor(code: Int = 0) : super() {
        this.code = code
    }

    constructor(code: Int = 0, message: String) : super(message) {
        this.code = code
    }
}