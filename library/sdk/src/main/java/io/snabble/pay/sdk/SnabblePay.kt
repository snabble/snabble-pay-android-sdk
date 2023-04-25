package io.snabble.pay.sdk

import android.content.Context
import io.snabble.pay.sdk.features.AccountSupport
import io.snabble.pay.sdk.features.CustomerInfoSupport
import io.snabble.pay.sdk.features.MandateSupport
import io.snabble.pay.sdk.features.SessionSupport

/**
 * Integration of SnabblePay is done by using an object of this interface. An instance can be
 * created using [io.snabble.pay.sdk.SnabblePay.getInstance].
 *
 * See each interface implemented by [SnabblePay] to check the capabilities
 *
 * @since 1.0.0
 */
interface SnabblePay : AccountSupport, CustomerInfoSupport, MandateSupport, SessionSupport {

    companion object {

        /**
         * Builder function to create an instance of [SnabblePay].
         *
         * Here is a sample of how to use this function:
         *
         * ```
         * val pay: SnabblePay = SnabblePay.getInstance(context = context) {
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
         *
         * @since 1.0.0
         */
        fun getInstance(
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
    }
}

internal class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
    customerInfoSupport: CustomerInfoSupport,
    mandateSupport: MandateSupport,
    sessionSupport: SessionSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    CustomerInfoSupport by customerInfoSupport,
    MandateSupport by mandateSupport,
    SessionSupport by sessionSupport
