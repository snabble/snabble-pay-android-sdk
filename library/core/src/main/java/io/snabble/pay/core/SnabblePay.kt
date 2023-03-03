package io.snabble.pay.core

import io.snabble.pay.core.features.AccountSupport
import io.snabble.pay.core.features.MandateSupport

interface SnabblePay : AccountSupport, MandateSupport

class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
    mandateSupport: MandateSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    MandateSupport by mandateSupport
