package io.snabble.pay.app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.utils.getMetaDataKey
import io.snabble.pay.core.BuildConfig
import io.snabble.pay.core.SnabblePay
import io.snabble.pay.core.dsl.snabblePay
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SnabblePayModule {

    @Provides
    @Singleton
    fun providesSnabblePay(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences,
    ): SnabblePay =
        snabblePay(context = context) {
            if (BuildConfig.DEBUG) {
                setBaseUrl(DEBUG_URL)
            }
            setSnabblePayKey(
                context.getMetaDataKey(KEY_META_DATA).trim()
            )
            setOnNewAppCredentialsCallback { id: String, secret: String ->
                sharedPreferences.saveAppCredentials(id, secret)
            }

            val appIdentifier =
                sharedPreferences.getStringSet(KEY_APP_IDENTIFIER, null) ?: return@snabblePay

            setAppCredentials(appIdentifier.first(), appIdentifier.last())
        }

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(KEY_SNABBLE_PAY_PREF, Context.MODE_PRIVATE)

    private fun SharedPreferences.saveAppCredentials(id: String, secret: String) =
        this.edit().putStringSet(KEY_APP_IDENTIFIER, setOf(id, secret)).apply()

    private const val KEY_META_DATA = "payment.snabble.key"
    private const val KEY_SNABBLE_PAY_PREF = "snabblePay"
    private const val KEY_APP_IDENTIFIER = "appIdentifier"
    private const val DEBUG_URL = "https://payment.snabble-testing.io"
}
