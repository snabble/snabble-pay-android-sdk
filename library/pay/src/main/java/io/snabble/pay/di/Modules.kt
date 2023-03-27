package io.snabble.pay.di

import io.snabble.pay.account.di.accountModule
import io.snabble.pay.di.modules.networkModule
import io.snabble.pay.di.modules.serviceModule
import io.snabble.pay.features.di.featureModule
import io.snabble.pay.internal.appcredentials.di.appCredentialsModule
import io.snabble.pay.internal.token.di.tokenModule
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
