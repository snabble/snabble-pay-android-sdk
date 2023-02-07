package io.snabble.pay.network.service.account

import io.snabble.pay.network.retrofit.ApiResponse
import io.snabble.pay.network.service.account.dto.AccountCheckDto
import io.snabble.pay.network.service.account.dto.AccountDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountService {

    /**
     * [API Endpoint Documentation](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#Account-Endpoints)
     */
    @GET("/apps/accounts/check")
    suspend fun getAccountCheck(@Query("appUri") appUri: String): ApiResponse<AccountCheckDto>

    @GET("/apps/accounts")
    suspend fun getAccounts(): ApiResponse<List<AccountDto>>

    @GET("/apps/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): ApiResponse<AccountDto>

    @DELETE("/apps/accounts/{id}")
    suspend fun removeAccount(@Path("id") id: String): ApiResponse<AccountDto>
}
