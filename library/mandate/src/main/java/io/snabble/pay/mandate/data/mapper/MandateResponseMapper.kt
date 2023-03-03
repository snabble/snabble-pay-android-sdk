package io.snabble.pay.mandate.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.mandate.data.dto.MandateStateDto
import io.snabble.pay.mandate.domain.model.MandateResponse

internal class MandateResponseMapper : Mapper<MandateResponse, MandateStateDto> {

    override fun map(from: MandateResponse): MandateStateDto = when (from) {
        MandateResponse.DECLINED -> MandateStateDto.DECLINED
        MandateResponse.ACCEPTED -> MandateStateDto.ACCEPTED
    }
}
