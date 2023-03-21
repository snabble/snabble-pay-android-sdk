package io.snabble.pay.internal.token.domain

import io.kotest.core.spec.style.FreeSpec
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import io.snabble.pay.internal.token.domain.repository.TokenRepository

class GetNewAccessTokenUseCaseImplTest : FreeSpec({

    val repo: TokenRepository = mockk(relaxed = true)

    fun createSut() = GetNewAccessTokenUseCaseImpl(
        tokenRepository = repo
    )

    beforeEach {
        clearAllMocks()
    }

    "getNewAccessToken() calls the repositories fun for a new token" {
        val sut = createSut()
        sut()

        coVerify(exactly = 1) { repo.getNewToken() }
        coVerify(exactly = 0) { repo.getToken() }
    }
})
