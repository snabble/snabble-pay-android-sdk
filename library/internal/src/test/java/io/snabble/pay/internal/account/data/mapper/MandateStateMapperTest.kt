package io.snabble.pay.internal.account.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.snabble.pay.internal.account.data.dto.MandateStateDto
import io.snabble.pay.shared.account.domain.model.Account

internal class MandateStateMapperTest : FreeSpec({

    fun createSut() = MandateStateMapper()

    "MandateState data model is mapped correctly to the state domain model" - {

        "ACCEPTED" {
            val sut = createSut()

            sut.map(from = MandateStateDto.ACCEPTED) shouldBe Account.MandateState.ACCEPTED
        }

        "DECLINED" {
            val sut = createSut()

            sut.map(from = MandateStateDto.DECLINED) shouldBe Account.MandateState.DECLINED
        }

        "MISSING" {
            val sut = createSut()

            sut.map(from = MandateStateDto.MISSING) shouldBe Account.MandateState.MISSING
        }

        "PENDING" {
            val sut = createSut()

            sut.map(from = MandateStateDto.PENDING) shouldBe Account.MandateState.PENDING
        }
    }
})
