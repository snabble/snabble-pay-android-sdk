package io.snabble.pay.customerinfo.domain.model

/**
 * CustomerInfo that is associated to a customer's transaction.
 *
 * @property id An optional ID
 * @property loyaltyId An optional LoyaltyID
 */
data class CustomerInfo(
    val id: String? = null,
    val loyaltyId: String? = null,
)
