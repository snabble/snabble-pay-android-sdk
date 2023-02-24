package io.snabble.pay.core.dsl

import android.content.Context
import io.snabble.pay.core.SnabblePay
import io.snabble.pay.core.SnabblePayConfiguration

typealias ConfigSetup = SnabblePayConfiguration.() -> Unit

fun snabblePay(
    context: Context,
    setup: ConfigSetup = {},
): SnabblePay = SnabblePayConfiguration.init()
    .apply {
        setup()
        ensureSnabblePayKeyOrThrow()
        setupDi(context)
    }
    .snabblePay
