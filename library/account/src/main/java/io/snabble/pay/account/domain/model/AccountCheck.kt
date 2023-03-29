package io.snabble.pay.account.domain.model

/**
 * An AccountCheck holds the [validationLink] a customer needs to open on his device to add a bank
 * account and the [appUri] called by the service to navigate back to the app.
 *
 * The navigation should be done using
 * [deep linking](https://developer.android.com/training/app-links/deep-linking).
 *
 * @property validationLink The link for the customer to add a new bank account using his device.
 * @property appUri The deep link the service calls after a successful bank account check.
 */
data class AccountCheck(
    val validationLink: String,
    val appUri: String,
)
