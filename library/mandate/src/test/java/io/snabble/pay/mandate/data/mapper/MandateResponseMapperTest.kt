package io.snabble.pay.mandate.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.snabble.pay.mandate.data.dto.MandateStateDto
import io.snabble.pay.mandate.domain.model.MandateResponse

internal class MandateResponseMapperTest : FreeSpec({

    fun createSut() = MandateResponseMapper()

    "MandateResponse domain model is mapped correctly to the dto model for state" - {

        "ACCEPTED" {
            val sut = createSut()

            sut.map(MandateResponse.ACCEPTED) shouldBe MandateStateDto.ACCEPTED
        }

        "DECLINED" {
            val sut = createSut()

            sut.map(MandateResponse.DECLINED) shouldBe MandateStateDto.DECLINED
        }
    }
})
