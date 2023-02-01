package io.snabble.pay.core.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

internal val coreModule = module {

    factory { PreferenceManager.getDefaultSharedPreferences(androidContext()) } bind SharedPreferences::class

}
