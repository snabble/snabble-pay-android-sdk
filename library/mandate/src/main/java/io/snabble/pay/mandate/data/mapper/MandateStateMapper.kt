package io.snabble.pay.mandate.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.mandate.data.dto.MandateStateDto
import io.snabble.pay.mandate.domain.model.Mandate.State

internal class MandateStateMapper : Mapper<MandateStateDto, State> {

    override fun map(from: MandateStateDto): State = when (from) {
        MandateStateDto.ACCEPTED -> State.ACCEPTED
        MandateStateDto.DECLINED -> State.DECLINED
        MandateStateDto.PENDING -> State.PENDING
    }
}
