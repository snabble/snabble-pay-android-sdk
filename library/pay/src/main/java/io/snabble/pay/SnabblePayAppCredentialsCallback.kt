package io.snabble.pay

fun interface SnabblePayAppCredentialsCallback {

    fun onNewAppCredentials(appId: String, appSecret: String)
}
