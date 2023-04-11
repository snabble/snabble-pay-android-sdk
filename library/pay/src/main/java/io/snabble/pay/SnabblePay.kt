package io.snabble.pay

import io.snabble.pay.features.AccountSupport
import io.snabble.pay.features.CustomerInfoSupport
import io.snabble.pay.features.MandateSupport
import io.snabble.pay.features.SessionSupport

/**
 * Integration of SnabblePay is done by using an object of this interface. An instance can be
 * created using [io.snabble.pay.dsl.snabblePay].
 *
 * See each interface implemented by [SnabblePay] to check the capabilities
 *
 * @since 1.0.0
 */
interface SnabblePay : AccountSupport, CustomerInfoSupport, MandateSupport, SessionSupport

internal class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
    customerInfoSupport: CustomerInfoSupport,
    mandateSupport: MandateSupport,
    sessionSupport: SessionSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    CustomerInfoSupport by customerInfoSupport,
    MandateSupport by mandateSupport,
    SessionSupport by sessionSupport
