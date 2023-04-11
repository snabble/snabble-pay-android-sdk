package io.snabble.pay.internal.di

import io.snabble.pay.internal.account.data.mapper.MandateStateMapper
import org.koin.dsl.module

val sharedModule = module {
    single { io.snabble.pay.internal.account.data.mapper.AccountMapper(mandateStateMapper = get()) }
    single { MandateStateMapper() }
}
