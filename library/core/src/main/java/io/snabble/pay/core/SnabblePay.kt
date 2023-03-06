package io.snabble.pay.core

import io.snabble.pay.core.features.AccountSupport
import io.snabble.pay.core.features.MandateSupport
import io.snabble.pay.core.features.SessionSupport

interface SnabblePay : AccountSupport, MandateSupport

class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
    mandateSupport: MandateSupport,
    sessionSupport: SessionSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    MandateSupport by mandateSupport,
    SessionSupport by sessionSupport
