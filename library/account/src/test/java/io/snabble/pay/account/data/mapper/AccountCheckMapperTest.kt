package io.snabble.pay.account.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.account.data.dto.AccountCheckDto

internal class AccountCheckMapperTest : FreeSpec({

    fun createSut() = AccountCheckMapper()

    "AccountCheckMapperTest should correctly map" - {

        "validationLink" {
            val mandateDto = mockk<AccountCheckDto>(relaxed = true) {
                every { validationLink } returns "https://example.com"
            }

            val sut = createSut()

            sut.map(from = mandateDto).validationLink shouldBe "https://example.com"
        }

        "appUri" {
            val mandateDto = mockk<AccountCheckDto>(relaxed = true) {
                every { appUri } returns "snabble-pay://sample"
            }

            val sut = createSut()

            sut.map(from = mandateDto).appUri shouldBe "snabble-pay://sample"
        }
    }
})
