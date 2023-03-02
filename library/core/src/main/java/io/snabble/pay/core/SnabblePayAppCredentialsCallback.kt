package io.snabble.pay.core

fun interface SnabblePayAppCredentialsCallback {

    fun onNewAppCredentials(appId: String, appSecret: String)
}
