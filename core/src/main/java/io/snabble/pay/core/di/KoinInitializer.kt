package io.snabble.pay.core.di

import android.content.Context
import androidx.startup.Initializer
import io.snabble.pay.core.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication

@Suppress("unused") // Usage in AndroidManifest.xml
internal class KoinInitializer : Initializer<Koin> {

    override fun create(context: Context): Koin =
        koinApplication {
            if (BuildConfig.DEBUG) printLogger(level = Level.DEBUG)
            androidContext(context)
            modules(
                coreModule,
            )
        }
            .koin
            .also(KoinProvider::setKoin)

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
