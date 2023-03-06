package io.snabble.pay.app.domain.home.usecase

interface GetSessionTokenUseCase {

    suspend operator fun invoke(id: Int): String

}

class GetSessionTokenUseCaseImpl : GetSessionTokenUseCase {

    override suspend fun invoke(id: Int): String {
        return "test"
    }
}
