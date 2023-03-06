package io.snabble.pay.core.di

import io.snabble.pay.account.di.accountModule
import io.snabble.pay.core.di.modules.networkModule
import io.snabble.pay.core.di.modules.serviceModule
import io.snabble.pay.core.features.di.featureModule
import io.snabble.pay.core.internal.appcredentials.di.appCredentialsModule
import io.snabble.pay.core.internal.token.di.tokenModule
import io.snabble.pay.mandate.di.mandateModule
import io.snabble.pay.session.di.sessionModule
import org.koin.core.module.Module

internal val koinModules: List<Module> = listOf(
    accountModule,
    appCredentialsModule,
    featureModule,
    mandateModule,
    networkModule,
    serviceModule,
    sessionModule,
    tokenModule
)
