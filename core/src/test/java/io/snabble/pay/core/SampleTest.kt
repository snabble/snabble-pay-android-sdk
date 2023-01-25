package io.snabble.pay.core

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class SampleTest : FreeSpec({

    "2 + 2" - {

        "should be 4" {
            val sum = 2 + 2
            sum shouldBe 4
        }
    }

    "2 + 1" - {

        "should be 3" {
            val sum = 2 + 1 + 1
            sum shouldBe 3
        }
    }
})
