package io.snabble.pay.api.util

fun interface Mapper<From, To> {

    fun map(from: From): To
}
