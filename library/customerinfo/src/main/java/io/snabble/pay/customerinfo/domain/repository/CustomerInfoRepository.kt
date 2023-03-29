package io.snabble.pay.customerinfo.domain.repository

import io.snabble.pay.core.util.Result
import io.snabble.pay.customerinfo.domain.model.CustomerInfo

/** @suppress Dokka */
interface CustomerInfoRepository {

    suspend fun createCustomerInfo(
        id: String? = null,
        loyaltyId: String? = null,
    ): Result<CustomerInfo>

    suspend fun getCustomerInfo(): Result<CustomerInfo>

    suspend fun removeCustomerInfo(): Result<CustomerInfo>
}
