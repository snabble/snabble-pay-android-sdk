package io.snabble.pay.account.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.snabble.pay.account.data.dto.MandateStateDto
import io.snabble.pay.account.domain.model.MandateState

internal class MandateStateMapperTest : FreeSpec({

    fun createSut() = MandateStateMapper()

    "MandateState data model is mapped correctly to the state domain model" - {

        "ACCEPTED" {
            val sut = createSut()

            sut.map(from = MandateStateDto.ACCEPTED) shouldBe MandateState.ACCEPTED
        }

        "DECLINED" {
            val sut = createSut()

            sut.map(from = MandateStateDto.DECLINED) shouldBe MandateState.DECLINED
        }

        "PENDING" {
            val sut = createSut()

            sut.map(from = MandateStateDto.PENDING) shouldBe MandateState.PENDING
        }
    }
})