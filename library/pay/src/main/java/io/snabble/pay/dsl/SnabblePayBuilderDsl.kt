package io.snabble.pay.dsl

import android.content.Context
import io.snabble.pay.SnabblePay
import io.snabble.pay.SnabblePayConfiguration
import io.snabble.pay.SnabblePayImpl

fun snabblePay(
    context: Context,
    init: SnabblePayConfiguration.() -> Unit = {},
): SnabblePay = with(SnabblePayConfiguration.create(context, init).koin) {
    SnabblePayImpl(
        accountSupport = get(),
        customerInfoSupport = get(),
        mandateSupport = get(),
        sessionSupport = get()
    )
}
