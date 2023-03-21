package io.snabble.pay

import io.snabble.pay.features.AccountSupport
import io.snabble.pay.features.MandateSupport
import io.snabble.pay.features.SessionSupport

interface SnabblePay : AccountSupport, MandateSupport, SessionSupport

class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
    mandateSupport: MandateSupport,
    sessionSupport: SessionSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    MandateSupport by mandateSupport,
    SessionSupport by sessionSupport
