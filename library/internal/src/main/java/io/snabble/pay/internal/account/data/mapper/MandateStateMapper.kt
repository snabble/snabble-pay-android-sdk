package io.snabble.pay.internal.account.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.internal.account.data.dto.MandateStateDto
import io.snabble.pay.shared.account.domain.model.Account.MandateState

class MandateStateMapper : Mapper<MandateStateDto, MandateState> {

    override fun map(from: MandateStateDto): MandateState = when (from) {
        MandateStateDto.ACCEPTED -> MandateState.ACCEPTED
        MandateStateDto.DECLINED -> MandateState.DECLINED
        MandateStateDto.MISSING -> MandateState.MISSING
        MandateStateDto.PENDING -> MandateState.PENDING
    }
}
