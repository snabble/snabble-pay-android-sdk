package io.snabble.pay.mandate.data.repository

import io.snabble.pay.api.util.toNullableResult
import io.snabble.pay.api.util.toResult
import io.snabble.pay.mandate.data.dto.MandateResponseDto
import io.snabble.pay.mandate.data.mapper.MandateMapper
import io.snabble.pay.mandate.data.mapper.MandateResponseMapper
import io.snabble.pay.mandate.data.service.MandateService
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateResponse
import io.snabble.pay.mandate.domain.repository.MandateRepository

internal class MandateRepositoryImpl(
    private val service: MandateService,
    private val mandateMapper: MandateMapper,
    private val mandateResponseMapper: MandateResponseMapper,
) : MandateRepository {

    override suspend fun createMandate(accountId: String): Result<Mandate> = service
        .createMandate(accountId)
        .toResult(mandateMapper::map)

    override suspend fun getMandate(accountId: String): Result<Mandate?> = service
        .getMandate(accountId = accountId)
        .toNullableResult(mandateMapper::map)

    override suspend fun respondToMandate(
        accountId: String,
        mandateId: String,
        response: MandateResponse,
    ): Result<Mandate> = service
        .respondToMandate(
            accountId = accountId,
            response = MandateResponseDto(
                mandateId = mandateId,
                state = mandateResponseMapper.map(from = response)
            )
        )
        .toResult(mandateMapper::map)
}
