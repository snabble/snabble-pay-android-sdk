package io.snabble.pay.core.features.di

import io.snabble.pay.core.features.MandateSupport
import io.snabble.pay.core.features.MandateSupportImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureModule = module {
    singleOf(::MandateSupportImpl) bind MandateSupport::class
}
