package io.snabble.pay.mandate.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.snabble.pay.mandate.data.dto.MandateStateDto
import io.snabble.pay.mandate.domain.model.MandateState

internal class MandateStateMapperTest : FreeSpec({

    fun createSut() = MandateStateMapper()

    "MandateState data model is mapped correctly to the domain model for state" - {

        "ACCEPTED" {
            val sut = createSut()

            sut.map(MandateStateDto.ACCEPTED) shouldBe MandateState.ACCEPTED
        }

        "DECLINED" {
            val sut = createSut()

            sut.map(MandateStateDto.DECLINED) shouldBe MandateState.DECLINED
        }

        "PENDING" {
            val sut = createSut()

            sut.map(MandateStateDto.PENDING) shouldBe MandateState.PENDING
        }
    }
})
