package io.snabble.pay.mandate.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.mandate.data.dto.MandateStateDto
import io.snabble.pay.mandate.domain.model.MandateState

internal class MandateStateMapper : Mapper<MandateStateDto, MandateState> {

    override fun map(from: MandateStateDto): MandateState = when (from) {
        MandateStateDto.ACCEPTED -> MandateState.ACCEPTED
        MandateStateDto.DECLINED -> MandateState.DECLINED
        MandateStateDto.PENDING -> MandateState.PENDING
    }
}
