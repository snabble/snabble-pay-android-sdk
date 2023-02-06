package io.snabble.pay.network.response

import okhttp3.Response

data class Response<T>(
    val data: T,
    val response: Response
)
