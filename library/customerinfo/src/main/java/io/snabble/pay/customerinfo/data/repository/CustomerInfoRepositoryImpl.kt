package io.snabble.pay.customerinfo.data.repository

import io.snabble.pay.api.util.toResult
import io.snabble.pay.core.util.Result
import io.snabble.pay.customerinfo.data.dto.CustomerInfoDto
import io.snabble.pay.customerinfo.data.mapper.CustomerInfoMapper
import io.snabble.pay.customerinfo.data.service.CustomerInfoService
import io.snabble.pay.customerinfo.domain.model.CustomerInfo
import io.snabble.pay.customerinfo.domain.repository.CustomerInfoRepository

internal class CustomerInfoRepositoryImpl(
    private val service: CustomerInfoService,
    private val mapper: CustomerInfoMapper,
) : CustomerInfoRepository {

    override suspend fun createCustomerInfo(
        id: String?,
        loyaltyId: String?,
    ): Result<CustomerInfo> = service
        .createCustomerInfo(CustomerInfoDto(id = id, loyaltyId = loyaltyId))
        .toResult(mapper::map)

    override suspend fun getCustomerInfo(): Result<CustomerInfo> = service
        .getCustomerInfo()
        .toResult(mapper::map)

    override suspend fun removeCustomerInfo(): Result<CustomerInfo> = service
        .removeCustomerInfo()
        .toResult(mapper::map)
}
