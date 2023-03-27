package io.snabble.pay.features.di

import io.snabble.pay.features.AccountSupport
import io.snabble.pay.features.AccountSupportImpl
import io.snabble.pay.features.MandateSupport
import io.snabble.pay.features.MandateSupportImpl
import io.snabble.pay.features.SessionSupport
import io.snabble.pay.features.SessionSupportImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureModule = module {
    singleOf(::AccountSupportImpl) bind AccountSupport::class
    singleOf(::MandateSupportImpl) bind MandateSupport::class
    singleOf(::SessionSupportImpl) bind SessionSupport::class
}
