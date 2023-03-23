package io.snabble.pay.customerinfo.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.customerinfo.domain.model.CustomerInfo

fun interface GetCustomerInfoUseCase {

    suspend operator fun invoke(): Result<CustomerInfo>
}
