package io.snabble.pay.session.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.session.data.dto.TokenDto
import java.time.ZonedDateTime

internal class TokenMapperTest : FreeSpec({

    fun createSut(): TokenMapper = TokenMapper()

    beforeEach {
        clearAllMocks()
    }

    "TokenMapper should correctly map the" - {

        "createdAt" {
            val now = ZonedDateTime.now()
            val tokenDto = mockk<TokenDto>(relaxed = true) {
                every { createdAt } returns now
            }

            val sut = createSut()

            sut.map(tokenDto).createdAt shouldBe now
        }

        "id" {
            val tokenId = "t1"
            val tokenDto = mockk<TokenDto>(relaxed = true) {
                every { id } returns tokenId
            }

            val sut = createSut()

            sut.map(tokenDto).id shouldBe tokenId
        }

        "refreshAt" {
            val now = ZonedDateTime.now()
            val tokenDto = mockk<TokenDto>(relaxed = true) {
                every { refreshAt } returns now
            }

            val sut = createSut()

            sut.map(tokenDto).refreshAt shouldBe now
        }

        "validUntil" {
            val now = ZonedDateTime.now()
            val tokenDto = mockk<TokenDto>(relaxed = true) {
                every { validUntil } returns now
            }

            val sut = createSut()

            sut.map(tokenDto).validUntil shouldBe now
        }

        "value" {
            val tokenValue = "Asd987"
            val tokenDto = mockk<TokenDto>(relaxed = true) {
                every { value } returns tokenValue
            }

            val sut = createSut()

            sut.map(tokenDto).value shouldBe tokenValue
        }
    }
})
