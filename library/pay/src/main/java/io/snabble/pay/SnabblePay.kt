package io.snabble.pay

import io.snabble.pay.features.AccountSupport
import io.snabble.pay.features.CustomerInfoSupport
import io.snabble.pay.features.MandateSupport
import io.snabble.pay.features.SessionSupport

interface SnabblePay : AccountSupport, CustomerInfoSupport, MandateSupport, SessionSupport

class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
    customerInfoSupport: CustomerInfoSupport,
    mandateSupport: MandateSupport,
    sessionSupport: SessionSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    CustomerInfoSupport by customerInfoSupport,
    MandateSupport by mandateSupport,
    SessionSupport by sessionSupport
