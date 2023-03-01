package io.snabble.pay.core.di

import io.snabble.pay.core.account.di.accountModule
import io.snabble.pay.core.di.modules.networkModule
import io.snabble.pay.core.di.modules.serviceModule
import io.snabble.pay.core.domain.mapper.di.domainMapperModule
import io.snabble.pay.core.internal.appcredentials.di.appCredentialsModule
import io.snabble.pay.core.internal.token.di.tokenModule
import org.koin.core.module.Module

internal val koinModules: List<Module> = listOf(
    accountModule,
    appCredentialsModule,
    domainMapperModule,
    networkModule,
    serviceModule,
    tokenModule
)