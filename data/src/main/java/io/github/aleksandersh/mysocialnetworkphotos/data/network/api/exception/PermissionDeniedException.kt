package io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception

class PermissionDeniedException : ApiException {

    constructor(code: Int) : super(code)

    constructor(code: Int, message: String) : super(code, message)
}