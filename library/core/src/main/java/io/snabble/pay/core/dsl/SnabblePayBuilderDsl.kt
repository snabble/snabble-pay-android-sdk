package io.snabble.pay.core.dsl

import android.content.Context
import io.snabble.pay.core.SnabblePay
import io.snabble.pay.core.SnabblePayConfiguration
import io.snabble.pay.core.SnabblePayImpl

fun snabblePay(
    context: Context,
    setup: SnabblePayConfiguration.() -> Unit = {},
): SnabblePay = SnabblePayImpl(
    configuration = SnabblePayConfiguration.init(context, setup)
)
