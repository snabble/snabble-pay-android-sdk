package io.snabble.pay.mandate.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.snabble.pay.mandate.data.dto.MandateDto
import io.snabble.pay.mandate.domain.model.MandateState

internal class MandateMapperTest : FreeSpec({

    val mandateStateMapper = mockk<MandateStateMapper>(relaxed = true)

    fun createSut(): MandateMapper = MandateMapper(mandateStateMapper = mandateStateMapper)

    beforeEach {
        clearAllMocks()
    }

    "MandateMapperTest should correctly map the" - {

        "htmlText" {
            val mandateDto = MandateDto(
                htmlText = "Hello World!",
                id = "",
                state = mockk()
            )
            val sut = createSut()

            sut.map(mandateDto).htmlText shouldBe "Hello World!"
        }

        "null htmlText" {
            val mandateDto = MandateDto(
                htmlText = null,
                id = "",
                state = mockk()
            )
            val sut = createSut()

            sut.map(mandateDto).htmlText.shouldBeNull()
        }

        "id" {
            val mandateDto = MandateDto(
                htmlText = "",
                id = "Qwerty123",
                state = mockk()
            )
            val sut = createSut()

            sut.map(mandateDto).id shouldBe "Qwerty123"
        }

        "state" {
            val mandateDto = MandateDto(
                htmlText = "",
                id = "",
                state = mockk()
            )
            val mandateStateMock = mockk<MandateState>()
            every { mandateStateMapper.map(any()) } returns mandateStateMock

            val sut = createSut()
            val mandate = sut.map(mandateDto)

            verify(exactly = 1) { mandateStateMapper.map(any()) }
            mandate.state shouldBe mandateStateMock
        }
    }
})
