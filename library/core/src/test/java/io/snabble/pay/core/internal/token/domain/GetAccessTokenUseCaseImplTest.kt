package io.snabble.pay.core.internal.token.domain

import io.kotest.core.spec.style.FreeSpec
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import io.snabble.pay.core.internal.token.domain.repository.TokenRepository

class GetAccessTokenUseCaseImplTest : FreeSpec({

    val repo: TokenRepository = mockk(relaxed = true)

    fun createSut() = GetAccessTokenUseCaseImpl(
        tokenRepository = repo
    )

    beforeEach {
        clearAllMocks()
    }

    "getAccessToken() calls the repositories fun for a new token" {
        val sut = createSut()
        sut()

        coVerify(exactly = 1) { repo.getToken() }
        coVerify(exactly = 0) { repo.getNewToken() }
    }
})
