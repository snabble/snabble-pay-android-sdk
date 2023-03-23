package io.snabble.pay.features

import io.snabble.pay.core.util.Result
import io.snabble.pay.customerinfo.domain.model.CustomerInfo
import io.snabble.pay.customerinfo.domain.usecase.CreateCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.GetCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.RemoveCustomerInfoUseCase

interface CustomerInfoSupport {

    suspend fun sendCustomerInfo(id: String?, loyaltyId: String?): Result<CustomerInfo>

    suspend fun getCustomerInfo(): Result<CustomerInfo>

    suspend fun removeCustomerInfo(): Result<CustomerInfo>
}

class CustomerInfoSupportImpl(
    private val createInfo: CreateCustomerInfoUseCase,
    private val getInfo: GetCustomerInfoUseCase,
    private val removeInfo: RemoveCustomerInfoUseCase,
) : CustomerInfoSupport {

    override suspend fun sendCustomerInfo(
        id: String?,
        loyaltyId: String?,
    ): Result<CustomerInfo> = createInfo(id = id, loyaltyId = loyaltyId)

    override suspend fun getCustomerInfo(): Result<CustomerInfo> = getInfo()

    override suspend fun removeCustomerInfo(): Result<CustomerInfo> = removeInfo()
}
