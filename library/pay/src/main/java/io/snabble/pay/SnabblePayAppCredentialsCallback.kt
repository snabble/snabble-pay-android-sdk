package io.snabble.pay

/**
 * Callback for new app credentials. This callback is only called if no
 * [SnabblePayConfiguration.appIdentifier] and no [SnabblePayConfiguration.appSecret] has been
 * provided.
 *
 * To make use of this callback set it in the builder functions [io.snabble.pay.dsl.snabblePay] init
 * block.
 *
 * **CAUTION:** Save the credentials provided through this callback to restore an app/user after the
 * [SnabblePay] instance has been destroyed.
 */
fun interface SnabblePayAppCredentialsCallback {

    /**
     * @param appId A new app identifier for the current app instance.
     * @param appSecret The secret for the new appId.
     */
    fun onNewAppCredentials(appId: String, appSecret: String)
}
