package io.snabble.pay.core

import io.snabble.pay.core.features.AccountSupport

interface SnabblePay : AccountSupport

class SnabblePayImpl internal constructor(
    accountSupport: AccountSupport,
) : SnabblePay,
    AccountSupport by accountSupport
