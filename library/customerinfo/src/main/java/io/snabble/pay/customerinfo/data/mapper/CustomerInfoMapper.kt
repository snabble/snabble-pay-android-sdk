package io.snabble.pay.customerinfo.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.customerinfo.data.dto.CustomerInfoDto
import io.snabble.pay.customerinfo.domain.model.CustomerInfo

internal class CustomerInfoMapper : Mapper<CustomerInfoDto, CustomerInfo> {

    override fun map(from: CustomerInfoDto): CustomerInfo = CustomerInfo(
        id = from.id,
        loyaltyId = from.loyaltyId
    )
}
