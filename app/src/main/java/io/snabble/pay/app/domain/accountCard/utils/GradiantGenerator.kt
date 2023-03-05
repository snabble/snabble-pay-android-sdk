package io.snabble.pay.app.domain.accountCard.utils

import kotlin.random.Random

class GradiantGenerator {

    private val mColors = arrayOf(
        "#39add1", // light blue [0]
        "#3079ab", // dark blue [1]
        "#c25975", // mauve [2]
        "#e15258", // red
        "#f9845b", // orange
        "#838cc7", // lavender
        "#7d669e", // purple
        "#53bbb4", // aqua
        "#51b46d", // green
        "#e0ab18", // mustard
        "#637a91", // dark gray
        "#f092b0", // pink
        "#b7c0c7" // light gray
    )

    private val mColors2 = arrayOf(
        "#3079ab", // dark blue  [0]
        "#39add1", // light blue [1]
        "#e0ab18", // mustard [2]
        "#637a91", // dark gray
        "#f092b0", // pink
        "#b7c0c7", // light gray
        "#c25975", // mauve
        "#51b46d", // green
        "#e15258", // red
        "#f9845b", // orange
        "#838cc7", // lavender
        "#7d669e", // purple
        "#53bbb4"
    )

    fun createGradiantBackground(): List<String> {
        val random = Random.nextInt(0, mColors.lastIndex)
        val colorOne = mColors[random]
        val colorTwo = mColors2[random]
        return listOf(
            colorOne, colorTwo
        )
    }
}
