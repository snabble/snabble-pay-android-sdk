package io.snabble.pay.core

import android.content.Context
import io.snabble.pay.core.di.koinModules
import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.internal.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.internal.appcredentials.domain.model.AppSecret
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class SnabblePayConfiguration private constructor() {

    internal var appCredentials: AppCredentials? = null
        private set

    internal var baseUrl = "https://payment.snabble.io"
        private set

    internal lateinit var snabblePayKey: String
        private set

    internal var onNewAppCredentialsCallback: SnabblePayAppCredentialsCallback? = null
        private set

    internal lateinit var koin: Koin

    fun setAppCredentials(appId: String, appSecret: String) {
        appCredentials = AppCredentials(id = AppIdentifier(appId), secret = AppSecret(appSecret))
    }

    fun setBaseUrl(url: String) {
        baseUrl = url
    }

    fun setOnNewAppCredentialsCallback(callback: SnabblePayAppCredentialsCallback) {
        onNewAppCredentialsCallback = callback
    }

    fun setSnabblePayKey(snabblePayKey: String) {
        this.snabblePayKey = snabblePayKey
    }

    private fun ensureSnabblePayKeyOrThrow() {
        assert(this::snabblePayKey.isInitialized) { "SnabblePayKey must be set." }
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

    companion object {

        internal fun init(
            context: Context,
            setup: SnabblePayConfiguration.() -> Unit,
        ): SnabblePayConfiguration =
            SnabblePayConfiguration()
                .apply {
                    setup()
                    ensureSnabblePayKeyOrThrow()
                    setupDi(context)
                }
    }
}
