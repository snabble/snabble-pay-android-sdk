package io.snabble.pay.core

import android.content.Context
import io.snabble.pay.core.appcredentials.data.source.remote.CustomerKey
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import io.snabble.pay.core.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class SnabblePayConfiguration private constructor() {

    val snabblePay: SnabblePay by lazy { SnabblePayImpl(this) }

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

    internal fun ensureSnabblePayKeyOrThrow() {
        assert(this::snabblePayKey.isInitialized) { "SnabblePayKey must be set." }
    }

    internal fun setupDi(context: Context) {
        val koinApplication = koinApplication {
            if (BuildConfig.DEBUG) printLogger(level = Level.DEBUG)
            androidContext(context.applicationContext)
            koin.loadModules(
                modules = listOf(
                    module {
                        single { this@SnabblePayConfiguration } bind SnabblePayConfiguration::class
                        single { CustomerKey(snabblePayKey) } bind CustomerKey::class
                    }
                )
            )
            modules(modules = koinModules)
        }
        koin = koinApplication.koin
    }

    companion object {

        internal fun init(): SnabblePayConfiguration = SnabblePayConfiguration()
    }
}
