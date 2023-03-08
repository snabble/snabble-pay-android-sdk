package io.snabble.pay.session.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.session.data.dto.TransactionDto
import io.snabble.pay.session.domain.model.TransactionState

internal class TransactionMapperTest : FreeSpec({

    val stateMapper: TransactionStateMapper = mockk(relaxed = true)

    fun createSut(): TransactionMapper = TransactionMapper(stateMapper = stateMapper)

    beforeEach {
        clearAllMocks()
    }

    "TransactionMapper should correctly map the" - {

        "amount" {
            val expectedAmount = "7"
            val tokenDto = mockk<TransactionDto>(relaxed = true) {
                every { amount } returns expectedAmount
            }

            val sut = createSut()

            sut.map(tokenDto).amount shouldBe expectedAmount
        }

        "currency" {
            val expectedCurrency = "EUR"
            val tokenDto = mockk<TransactionDto>(relaxed = true) {
                every { currency } returns expectedCurrency
            }

            val sut = createSut()

            sut.map(tokenDto).currency shouldBe expectedCurrency
        }

        "id" {
            val expectedId = "t1"
            val tokenDto = mockk<TransactionDto>(relaxed = true) {
                every { id } returns expectedId
            }

            val sut = createSut()

            sut.map(tokenDto).id shouldBe expectedId
        }

        "state" {
            val expectedState = mockk<TransactionState>()
            every { stateMapper.map(any()) } returns expectedState

            val sut = createSut()

            sut.map(mockk(relaxed = true)).state shouldBe expectedState
        }
    }
})
