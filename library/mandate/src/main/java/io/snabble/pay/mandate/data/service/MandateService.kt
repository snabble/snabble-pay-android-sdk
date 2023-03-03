package io.snabble.pay.mandate.data.service

import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.mandate.data.dto.MandateDto
import io.snabble.pay.mandate.data.dto.MandateResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

internal interface MandateService {

    @POST("/apps/accounts/{id}/mandate")
    suspend fun createMandate(@Path("id") accountId: String): ApiResponse<MandateDto>

    @GET("/apps/accounts/{id}/mandate")
    suspend fun getMandate(@Path("id") accountId: String): ApiResponse<MandateDto>

    @PATCH("/apps/accounts/{id}/mandate")
    suspend fun respondToMandate(
        @Path("id") accountId: String,
        @Body response: MandateResponseDto,
    ): ApiResponse<MandateDto>
}
