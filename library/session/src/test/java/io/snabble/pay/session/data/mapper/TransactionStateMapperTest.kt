package io.snabble.pay.session.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.snabble.pay.session.data.dto.TransactionStateDto
import io.snabble.pay.session.domain.model.TransactionState

internal class TransactionStateMapperTest : FreeSpec({

    fun createSut() = TransactionStateMapper()

    "MandateState data model is mapped correctly to the domain model for state" - {

        "ABORTED" {
            val sut = createSut()

            sut.map(TransactionStateDto.ABORTED) shouldBe TransactionState.ABORTED
        }

        "ERRORED" {
            val sut = createSut()

            sut.map(TransactionStateDto.ERRORED) shouldBe TransactionState.ERRORED
        }

        "FAILED" {
            val sut = createSut()

            sut.map(TransactionStateDto.FAILED) shouldBe TransactionState.FAILED
        }

        "PREAUTHORIZED" {
            val sut = createSut()

            sut.map(TransactionStateDto.PREAUTHORIZED) shouldBe TransactionState.PREAUTHORIZED
        }

        "PREAUTH_FAILED" {
            val sut = createSut()

            sut.map(TransactionStateDto.PREAUTH_FAILED) shouldBe TransactionState.PREAUTH_FAILED
        }

        "SUCCESSFUL" {
            val sut = createSut()

            sut.map(TransactionStateDto.SUCCESSFUL) shouldBe TransactionState.SUCCESSFUL
        }
    }
})
