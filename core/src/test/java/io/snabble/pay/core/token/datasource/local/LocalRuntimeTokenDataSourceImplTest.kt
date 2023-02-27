package io.snabble.pay.core.token.datasource.local

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.snabble.pay.core.token.datasource.Token
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import java.time.ZonedDateTime

class LocalRuntimeTokenDataSourceImplTest : FreeSpec({

    fun createSut() = LocalRuntimeTokenDataSourceImpl()

    beforeEach {
        clearAllMocks()
    }

    "getToken() returns" - {

        "the token that's been saved previously" {
            val expectedToken = Token(
                accessToken = AccessToken("Bearer qwerty"),
                expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
            )

            val sut = createSut().apply {
                token = expectedToken
            }
            val token = sut.getToken()

            token shouldBe Token(
                accessToken = AccessToken("Bearer qwerty"),
                expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
            )
        }

        "null if token is not available" {
            val sut = createSut()
            val token = sut.getToken()

            token.shouldBeNull()
        }
    }

    "saveToken(Token) saves the token" {
        val token = Token(
            accessToken = AccessToken("Bearer qwerty"),
            expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
        )

        val sut = createSut()
        sut.saveToken(token)

        sut.token shouldBe token
    }
})
