package io.snabble.pay.app.domain.account.utils

import kotlin.random.Random

class GradiantGenerator {

    private val mColors = arrayOf(
        "#FFCCFF",
        "#CCDDFF",
        "#FFDDCC",
        "#CCDDCC"
    )

    private val mColors2 = arrayOf(
        "#FFEEFF",
        "#CCFFFF",
        "#FFFFCC",
        "#CCFFCC"
    )

    fun createGradiantBackground(): List<String> {
        val random = Random.nextInt(0, mColors.lastIndex)
        val colorOne = mColors[random]
        val colorTwo = mColors2[random]
        return listOf(
            colorOne,
            colorTwo
        )
    }

    companion object {

        fun createGradiantColorList() =
            GradiantGenerator().createGradiantBackground()
    }
}
