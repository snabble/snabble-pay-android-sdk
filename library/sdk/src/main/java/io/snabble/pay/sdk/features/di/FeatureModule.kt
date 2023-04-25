package io.snabble.pay.sdk.features.di

import io.snabble.pay.sdk.features.AccountSupport
import io.snabble.pay.sdk.features.AccountSupportImpl
import io.snabble.pay.sdk.features.CustomerInfoSupport
import io.snabble.pay.sdk.features.CustomerInfoSupportImpl
import io.snabble.pay.sdk.features.MandateSupport
import io.snabble.pay.sdk.features.MandateSupportImpl
import io.snabble.pay.sdk.features.SessionSupport
import io.snabble.pay.sdk.features.SessionSupportImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val featureModule = module {
    singleOf(::AccountSupportImpl) bind AccountSupport::class
    singleOf(::CustomerInfoSupportImpl) bind CustomerInfoSupport::class
    singleOf(::MandateSupportImpl) bind MandateSupport::class
    singleOf(::SessionSupportImpl) bind SessionSupport::class
}
