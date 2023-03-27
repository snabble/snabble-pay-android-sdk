package io.snabble.pay.session.data.service

import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.session.data.dto.AccountIdDto
import io.snabble.pay.session.data.dto.SessionDto
import io.snabble.pay.session.data.dto.TokenDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface SessionService {

    @POST("/apps/sessions")
    suspend fun createSession(@Body accountId: AccountIdDto): ApiResponse<SessionDto>

    @DELETE("/apps/sessions/{id}")
    suspend fun deleteSession(@Path("id") sessionId: String): ApiResponse<SessionDto>

    @GET("/apps/sessions/{id}")
    suspend fun getSession(@Path("id") sessionId: String): ApiResponse<SessionDto>

    @GET("/apps/sessions")
    suspend fun getSessions(): ApiResponse<List<SessionDto>>

    @POST("/apps/sessions/{id}/token")
    suspend fun updateToken(@Path("id") sessionId: String): ApiResponse<TokenDto>
}
