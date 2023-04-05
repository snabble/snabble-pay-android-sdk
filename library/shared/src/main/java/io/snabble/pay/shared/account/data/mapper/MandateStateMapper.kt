package io.snabble.pay.shared.account.data.mapper

import io.snabble.pay.shared.account.data.dto.MandateStateDto
import io.snabble.pay.shared.account.domain.model.MandateState
import io.snabble.pay.api.util.Mapper

class MandateStateMapper : Mapper<MandateStateDto, MandateState> {

    override fun map(from: MandateStateDto): MandateState = when (from) {
        MandateStateDto.ACCEPTED -> MandateState.ACCEPTED
        MandateStateDto.DECLINED -> MandateState.DECLINED
        MandateStateDto.MISSING -> MandateState.MISSING
        MandateStateDto.PENDING -> MandateState.PENDING
    }
}
