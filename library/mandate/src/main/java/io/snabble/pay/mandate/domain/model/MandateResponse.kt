package io.snabble.pay.mandate.domain.model

/**
 * Represents the state of a MandateResponse.
 *
 * The response state is final and cannot be altered. A new has to be created if the mandate has
 * been declined and customer should be asked again.
 *
 * @since 1.0.0
 */
enum class MandateResponse {

    /**
     * Final state if the mandate has been accepted by the customer.
     *
     * @since 1.0.0
     */
    ACCEPTED,

    /**
     * Final state fit he mandate has been declined by the customer.
     *
     * @since 1.0.0
     */
    DECLINED,
}
