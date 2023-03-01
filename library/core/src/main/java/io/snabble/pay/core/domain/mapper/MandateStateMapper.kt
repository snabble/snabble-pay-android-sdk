package io.snabble.pay.core.domain.mapper

import io.snabble.pay.api.service.account.dto.MandateStateDto
import io.snabble.pay.core.domain.model.MandateState
import io.snabble.pay.core.util.Mapper

class MandateStateMapper : Mapper<MandateStateDto, MandateState> {

    override fun map(from: MandateStateDto): MandateState = when (from) {
        MandateStateDto.ACCEPTED -> MandateState.ACCEPTED
        MandateStateDto.DECLINED -> MandateState.DECLINED
        MandateStateDto.PENDING -> MandateState.PENDING
    }
}
