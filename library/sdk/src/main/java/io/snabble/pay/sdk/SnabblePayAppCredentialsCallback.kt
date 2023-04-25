package io.snabble.pay.sdk

/**
 * Callback for new app credentials. This callback is only called if no
 * [SnabblePayConfiguration.appIdentifier] and no [SnabblePayConfiguration.appSecret] has been
 * provided.
 *
 * To make use of this callback set it in the builder functions [io.snabble.pay.sdk.SnabblePay.getInstance] init
 * block.
 *
 * **CAUTION:** Save the credentials provided through this callback to restore an app/user after the
 * [SnabblePay] instance has been destroyed.
 *
 * @since 1.0.0
 */
fun interface SnabblePayAppCredentialsCallback {

    /**
     * @param appId A new app identifier for the current app instance.
     * @param appSecret The secret for the new appId.
     */
    fun onNewAppCredentials(appId: String, appSecret: String)
}
