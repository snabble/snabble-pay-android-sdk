package io.snabble.pay.customerinfo.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.customerinfo.domain.model.CustomerInfo

fun interface CreateCustomerInfoUseCase {

    suspend operator fun invoke(id: String?, loyaltyId: String?): Result<CustomerInfo>
}
