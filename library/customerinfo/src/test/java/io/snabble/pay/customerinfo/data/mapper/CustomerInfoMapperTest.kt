package io.snabble.pay.customerinfo.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.customerinfo.data.dto.CustomerInfoDto

internal class CustomerInfoMapperTest : FreeSpec({

    fun createSut(): CustomerInfoMapper = CustomerInfoMapper()

    beforeEach {
        clearAllMocks()
    }

    "AccountCheckMapperTest should correctly map" - {

        "id" {
            val expected = "qwerty123"
            val dto = mockk<CustomerInfoDto>(relaxed = true) {
                every { id } returns expected
            }

            val sut = createSut()

            sut.map(from = dto).id shouldBe expected
        }

        "loyaltyId" {
            val expected = "asd123"
            val dto = mockk<CustomerInfoDto>(relaxed = true) {
                every { loyaltyId } returns expected
            }

            val sut = createSut()

            sut.map(from = dto).loyaltyId shouldBe expected
        }
    }
})
