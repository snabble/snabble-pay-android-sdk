package io.snabble.pay.dsl

import android.content.Context
import io.snabble.pay.SnabblePay
import io.snabble.pay.SnabblePayConfiguration
import io.snabble.pay.SnabblePayImpl

fun snabblePay(
    context: Context,
    setup: SnabblePayConfiguration.() -> Unit = {},
): SnabblePay {
    val configuration = SnabblePayConfiguration.init(context, setup)
    return SnabblePayImpl(
        accountSupport = configuration.koin.get(),
        mandateSupport = configuration.koin.get(),
        sessionSupport = configuration.koin.get()
    )
}
