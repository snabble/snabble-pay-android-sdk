package io.snabble.pay.app.domain.home.usecase

import javax.inject.Inject

interface GetSessionTokenUseCase {

    suspend operator fun invoke(id: String): String
}

class GetSessionTokenUseCaseImpl @Inject constructor(): GetSessionTokenUseCase  {

    override suspend fun invoke(id: String): String {
        return "TODO"
    }
}
