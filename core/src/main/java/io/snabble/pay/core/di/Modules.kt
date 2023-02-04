package io.snabble.pay.core.di

import io.snabble.pay.core.appcredentials.di.appCredentials
import io.snabble.pay.core.di.modules.services
import org.koin.core.module.Module

internal val koinModules: List<Module> = listOf(
    services,
    appCredentials
)
