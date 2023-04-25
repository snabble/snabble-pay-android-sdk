package io.snabble.pay.sdk.di

import io.snabble.pay.account.di.accountModule
import io.snabble.pay.customerinfo.di.customerInfoModule
import io.snabble.pay.mandate.di.mandateModule
import io.snabble.pay.sdk.di.modules.networkModule
import io.snabble.pay.sdk.di.modules.serviceModule
import io.snabble.pay.sdk.features.di.featureModule
import io.snabble.pay.sdk.internal.appcredentials.di.appCredentialsModule
import io.snabble.pay.sdk.internal.token.di.tokenModule
import io.snabble.pay.session.di.sessionModule
import org.koin.core.module.Module

internal val koinModules: List<Module> = listOf(
    accountModule,
    appCredentialsModule,
    customerInfoModule,
    featureModule,
    mandateModule,
    networkModule,
    serviceModule,
    sessionModule,
    tokenModule
)
