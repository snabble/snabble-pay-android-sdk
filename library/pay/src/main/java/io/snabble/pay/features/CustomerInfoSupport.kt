package io.snabble.pay.features

import io.snabble.pay.core.util.Result
import io.snabble.pay.customerinfo.domain.model.CustomerInfo
import io.snabble.pay.customerinfo.domain.usecase.CreateCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.GetCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.RemoveCustomerInfoUseCase

/**
 * Interface with features related to the customer's transactions, helping identifying the customer
 * from transaction point of view.
 */
interface CustomerInfoSupport {

    /**
     * Set optional customer info that's associated with all future customer's transactions.
     *
     * @param id An optional [String], stored as id.
     * @param loyaltyId An optional [String], stored as loyaltyId.
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) containing the [CustomerInfo]
     * that's been set, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun sendCustomerInfo(id: String?, loyaltyId: String?): Result<CustomerInfo>

    /**
     * Get the [CustomerInfo] that's been set for the current customer.
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) with the [CustomerInfo], or
     * [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun getCustomerInfo(): Result<CustomerInfo>

    /**
     * Remove the customer info associated with the current customer.
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) with the [CustomerInfo] that's
     * been deleted, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun removeCustomerInfo(): Result<CustomerInfo>
}

internal class CustomerInfoSupportImpl(
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
