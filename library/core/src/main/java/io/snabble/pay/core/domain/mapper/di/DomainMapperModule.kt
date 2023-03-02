package io.snabble.pay.core.domain.mapper.di

import io.snabble.pay.core.domain.mapper.AccountCheckMapper
import io.snabble.pay.core.domain.mapper.AccountMapper
import io.snabble.pay.core.domain.mapper.MandateStateMapper
import org.koin.dsl.module

val domainMapperModule = module {
    single { AccountMapper(mandateStateMapper = get()) }
    single { MandateStateMapper() }
    single { AccountCheckMapper() }
}
