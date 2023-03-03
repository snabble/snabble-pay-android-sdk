package io.snabble.pay.core.di.modules

import io.snabble.pay.api.service.account.AccountService
import io.snabble.pay.api.service.register.AppRegistrationService
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        get<Retrofit>(named(AUTH_FREE_RETROFIT)).create(AppRegistrationService::class.java)
    } bind AppRegistrationService::class

    single { get<Retrofit>().create(AccountService::class.java) } bind AccountService::class
}
