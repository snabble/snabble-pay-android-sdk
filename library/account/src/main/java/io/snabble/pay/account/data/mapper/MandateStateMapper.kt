package io.snabble.pay.account.data.mapper

import io.snabble.pay.account.data.dto.MandateStateDto
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.api.util.Mapper

internal class MandateStateMapper : Mapper<MandateStateDto, MandateState> {

    override fun map(from: MandateStateDto): MandateState = when (from) {
        MandateStateDto.ACCEPTED -> MandateState.ACCEPTED
        MandateStateDto.DECLINED -> MandateState.DECLINED
        MandateStateDto.MISSING -> MandateState.MISSING
        MandateStateDto.PENDING -> MandateState.PENDING
    }
}
