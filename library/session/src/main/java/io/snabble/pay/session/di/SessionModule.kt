package io.snabble.pay.session.di

import io.snabble.pay.session.data.mapper.SessionMapper
import io.snabble.pay.session.data.mapper.TokenMapper
import io.snabble.pay.session.data.mapper.TransactionMapper
import io.snabble.pay.session.data.mapper.TransactionStateMapper
import io.snabble.pay.session.data.repository.SessionRepositoryImpl
import io.snabble.pay.session.data.service.SessionService
import io.snabble.pay.session.domain.repository.SessionRepository
import io.snabble.pay.session.domain.usecase.CreateSessionUseCase
import io.snabble.pay.session.domain.usecase.DeleteSessionUseCase
import io.snabble.pay.session.domain.usecase.GetAllSessionsUseCase
import io.snabble.pay.session.domain.usecase.GetSessionUseCase
import io.snabble.pay.session.domain.usecase.UpdateSessionTokenUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val sessionModule = module {
    factory { CreateSessionUseCase(get<SessionRepository>()::createSession) }
    factory { DeleteSessionUseCase(get<SessionRepository>()::deleteSession) }
    factory { GetAllSessionsUseCase(get<SessionRepository>()::getSessions) }
    factory { GetSessionUseCase(get<SessionRepository>()::getSession) }
    factory { UpdateSessionTokenUseCase(get<SessionRepository>()::updateToken) }

    singleOf(::SessionRepositoryImpl) bind SessionRepository::class

    factoryOf(::SessionMapper)
    factoryOf(::TokenMapper)
    factoryOf(::TransactionMapper)
    factoryOf(::TransactionStateMapper)

    single { get<Retrofit>().create(SessionService::class.java) } bind SessionService::class
}
