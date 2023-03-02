package io.snabble.pay.mandate.service

import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.mandate.service.dto.MandateDto
import io.snabble.pay.mandate.service.dto.MandateResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MandateService {

    @POST("/apps/accounts/{id}/mandate")
    fun createMandate(
        @Path("id") accountId: String,
    ): ApiResponse<MandateDto>

    @PATCH("/apps/accounts/{id}/mandate")
    fun respondToMandate(
        @Path("id") accountId: String,
        @Body response: MandateResponseDto,
    ): ApiResponse<MandateResponseDto>

    @GET("/apps/accounts/{id}/mandate")
    fun getMandateState(
        @Path("id") accountId: String,
    ): ApiResponse<MandateDto>
}
