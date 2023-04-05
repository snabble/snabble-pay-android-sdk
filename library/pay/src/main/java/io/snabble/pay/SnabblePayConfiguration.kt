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

class SnabblePayConfiguration private constructor() {

    var appIdentifier: String? = null

    var appSecret: String? = null

    var baseUrl = "https://payment.snabble.io"

    lateinit var snabblePayKey: String

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
