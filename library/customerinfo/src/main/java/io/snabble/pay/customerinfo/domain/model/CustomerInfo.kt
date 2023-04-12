package io.snabble.pay.customerinfo.domain.model

/**
 * CustomerInfo that is associated to a customer's transaction.
 *
 * @property id An optional ID
 * @property loyaltyId An optional LoyaltyID
 *
 * @since 1.0.0
 */
data class CustomerInfo(
    val id: String? = null,
    val loyaltyId: String? = null,
)
