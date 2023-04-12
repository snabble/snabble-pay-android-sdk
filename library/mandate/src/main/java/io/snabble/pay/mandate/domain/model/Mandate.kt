package io.snabble.pay.mandate.domain.model

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
