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

    "MandateMapper should correctly map the" - {

        "htmlText" {
            val mandateDto = mockk<MandateDto>(relaxed = true) {
                every { htmlText } returns "Hello World!"
            }

            val sut = createSut()

            sut.map(from = mandateDto).htmlText shouldBe "Hello World!"
        }

        "null htmlText" {
            val mandateDto = mockk<MandateDto>(relaxed = true) {
                every { htmlText } returns null
            }

            val sut = createSut()

            sut.map(from = mandateDto).htmlText.shouldBeNull()
        }

        "id" {
            val mandateDto = mockk<MandateDto>(relaxed = true) {
                every { id } returns "Qwerty123"
            }

            val sut = createSut()

            sut.map(from = mandateDto).id shouldBe "Qwerty123"
        }

        "state" {
            val mandateStateMock = mockk<MandateState>()
            every { mandateStateMapper.map(from = any()) } returns mandateStateMock

            val sut = createSut()
            val mandate = sut.map(from = mockk(relaxed = true))

            verify(exactly = 1) { mandateStateMapper.map(from = any()) }
            mandate.state shouldBe mandateStateMock
        }
    }
})
