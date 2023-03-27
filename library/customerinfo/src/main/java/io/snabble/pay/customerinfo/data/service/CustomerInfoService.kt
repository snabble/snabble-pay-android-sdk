package io.snabble.pay.customerinfo.data.service

import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.customerinfo.data.dto.CustomerInfoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

internal interface CustomerInfoService {

    @PUT("/apps/customer")
    suspend fun createCustomerInfo(
        @Body customerInfo: CustomerInfoDto,
    ): ApiResponse<CustomerInfoDto>

    @GET("/apps/customer")
    suspend fun getCustomerInfo(): ApiResponse<CustomerInfoDto>

    @DELETE("/apps/customer")
    suspend fun removeCustomerInfo(): ApiResponse<CustomerInfoDto>
}
