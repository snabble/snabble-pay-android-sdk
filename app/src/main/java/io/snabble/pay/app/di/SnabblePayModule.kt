package io.snabble.pay.app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
                setBaseUrl("https://payment.snabble-testing.io")
            }
            setOnNewAppCredentialsCallback { id: String, secret: String ->
                sharedPreferences
                    .edit()
                    .putString("appId", id)
                    .putString("appSecret", secret)
                    .apply()

            }
            setAppCredentials(
                appId = "772c5363-bfc9-4ce5-9a56-c0076f67c022",
                appSecret = "InV3ecGpglu19ZLGMunPSQys3Kh6kygFJAe59xU1Xooy3faEFJQabMW6ih17ABT84z/Y2mGu72YquScaVaDw=="
            )
            setSnabblePayKey(
                "IO2wX69CsqZUQ3HshOnRkO4y5Gy/kRar6Fnvkp94piA2ivUun7TC7MjukrgUKlu7g8W8/enVsPDT7Kvq28ycw=="
            )
        }

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context) =
        context.getSharedPreferences("snabblePay", Context.MODE_PRIVATE)
}
