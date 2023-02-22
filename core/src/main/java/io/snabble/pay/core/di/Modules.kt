package io.snabble.pay.core.di

import io.snabble.pay.core.appcredentials.di.appCredentialsModule
import io.snabble.pay.core.di.modules.networkModule
import io.snabble.pay.core.di.modules.serviceModule
import io.snabble.pay.core.token.di.tokenModule
import org.koin.core.module.Module

internal val koinModules: List<Module> = listOf(
    appCredentialsModule,
    networkModule,
    serviceModule,
    tokenModule
)
