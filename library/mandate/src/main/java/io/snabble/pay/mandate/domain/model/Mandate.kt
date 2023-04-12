package io.snabble.pay.mandate.domain.model

/**
 * Class representing the Mandate.
 *
 * A Mandate's property is optional, since it is not required if the mandate is already accepted or declined.
 *
 * @property htmlText Mandate terms to be accepted or declined
 * @property id Unique identifier for the mandate
 * @property state Current state of the mandate, e.g.
 * [PENDING](io.snabble.pay.mandate.domain.model.MandateState.PENDING),
 * [ACCEPTED](io.snabble.pay.mandate.domain.model.MandateState.ACCEPTED) or
 * [DECLINED](io.snabble.pay.mandate.domain.model.MandateState.DECLINED)
 *
 * @since 1.0.0
 */
class Mandate(
    val htmlText: String?,
    val id: String,
    val state: State,
) {

    enum class State {

        ACCEPTED,
        DECLINED,
        PENDING,
    }
}
