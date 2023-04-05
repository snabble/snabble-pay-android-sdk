package io.snabble.pay.shared.account.di

import io.snabble.pay.shared.account.data.mapper.AccountMapper
import io.snabble.pay.shared.account.data.mapper.MandateStateMapper
import org.koin.dsl.module

val sharedModule = module {
    single { AccountMapper(mandateStateMapper = get()) }
    single { MandateStateMapper() }
}
