package io.snabble.pay.core.util

fun interface Mapper<From, To> {

    fun map(from: From): To
}
