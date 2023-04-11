package io.snabble.pay.dsl

import android.content.Context
import io.snabble.pay.SnabblePay
import io.snabble.pay.SnabblePayConfiguration
import io.snabble.pay.SnabblePayImpl

/**
 * Builder function to create an instance of [SnabblePay].
 *
 * Here is a sample of how to use this function:
 *
 * ```
 * val pay: SnabblePay = snabblePay(context = context) {
 *     baseUrl = https://payment.snabble-staging.io
 *
 *     snabblePayKey = "shOnRkO4y5Gy..."
 *     onNewAppCredentialsCallback =
 *         SnabblePayAppCredentialsCallback { id: String, secret: String ->
 *             sharedPreferences.saveAppCredentials(id, secret)
 *         }
 *
 *     val (id, secret) = sharedPreferences.getAppCredentials() ?: return@snabblePay
 *
 *     appIdentifier = id
 *     appSecret = secret
 * }
 * ```
 *
 * @param context An Android [android.content.Context]
 * @param init The init block to create a [SnabblePayConfiguration] for [SnabblePay]
 *
 * @return An instance of [SnabblePay] using the configuration from the [init] block
 */
fun snabblePay(
    context: Context,
    init: SnabblePayConfiguration.() -> Unit = {},
): SnabblePay = with(SnabblePayConfiguration.create(context, init).koin) {
    SnabblePayImpl(
        accountSupport = get(),
        customerInfoSupport = get(),
        mandateSupport = get(),
        sessionSupport = get()
    )
}
