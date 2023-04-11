package io.snabble.pay

import android.content.Context
import io.snabble.pay.di.koinModules
import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.internal.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.internal.appcredentials.domain.model.AppSecret
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

/**
 * A configuration that is supplied to a new [SnabblePay] instance.
 *
 * The properties must be set by the building function [io.snabble.pay.dsl.snabblePay].
 */
class SnabblePayConfiguration private constructor() {

    /**
     * Individual identifier for an app instance or user
     *
     * @see [SnabblePayAppCredentialsCallback]
     */
    var appIdentifier: String? = null

    /**
     * Individual secret for the [appIdentifier]
     *
     * @see [SnabblePayAppCredentialsCallback]
     */
    var appSecret: String? = null

    /**
     * baseUrl The base url for Snabble Pay, the default url is the one for production.
     *
     * Available Environments:
     * * production - _https://payment.snabble.io_
     * * staging - _https://payment.snabble-staging.io_
     * * testing - _https://payment.snabble-testing.io_
     */
    var baseUrl = "https://payment.snabble.io"

    /**
     * Key to identify the project using SnabblePay _(supplied by Snabble)_.
     *
     * This key is necessary to integrate and provide Snabble Pay for the app users.
     */
    lateinit var snabblePayKey: String

    /**
     * Callback for a new [appIdentifier] and [appSecret] if none has been provided.
     *
     * Each time a new instance of [SnabblePay] is created without an appIdentifier and appSecret
     * new credentials are created and this callback gets called, if set.
     *
     * **CAUTION:** Save the credentials provided through this callback to restore a user after the
     * [SnabblePay] instance has been destroyed.
     */
    var onNewAppCredentialsCallback: SnabblePayAppCredentialsCallback? = null

    internal lateinit var koin: Koin

    internal val appCredentials: AppCredentials?
        get() {
            val id = appIdentifier ?: return null
            val secret = appSecret ?: return null
            return AppCredentials(id = AppIdentifier(id), secret = AppSecret(secret))
        }

    private fun ensureSnabblePayKeyOrThrow() {
        assert(::snabblePayKey.isInitialized) { "SnabblePayKey must be set." }
    }

    private fun setupDi(context: Context) {
        val koinApplication = koinApplication {
            if (BuildConfig.DEBUG) printLogger(level = Level.DEBUG)
            androidContext(context.applicationContext)
            modules(koinModules)
            modules(
                module {
                    single { this@SnabblePayConfiguration } bind SnabblePayConfiguration::class
                }
            )
        }
        koin = koinApplication.koin
    }

    /** @suppress */
    companion object {

        internal fun create(
            context: Context,
            init: SnabblePayConfiguration.() -> Unit,
        ): SnabblePayConfiguration = SnabblePayConfiguration().apply {
            init()
            ensureSnabblePayKeyOrThrow()
            setupDi(context)
        }
    }
}
