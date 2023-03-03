package io.snabble.pay.mandate.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.mandate.data.dto.MandateDto
import io.snabble.pay.mandate.domain.model.Mandate

internal class MandateMapper(
    private val mandateStateMapper: MandateStateMapper,
) : Mapper<MandateDto, Mandate> {

    override fun map(from: MandateDto): Mandate = Mandate(
        htmlText = from.htmlText,
        id = from.id,
        state = mandateStateMapper.map(from.state)
    )
}
