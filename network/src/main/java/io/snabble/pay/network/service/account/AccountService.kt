package io.snabble.pay.network.service.account

import io.snabble.pay.network.service.account.dto.AccountCheckDto
import io.snabble.pay.network.service.account.dto.AccountDto
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountService {

    /**
     * [API Endpoint Documentation](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#Account-Endpoints)
     */
    @GET("/apps/accounts/check")
    fun getAccountCheck(
        @Query("appUri") appUri: String,
    ): Call<AccountCheckDto>

    @GET("/apps/accounts")
    fun getAccounts(): Call<List<AccountDto>>

    @GET("/apps/accounts/{id}")
    fun getAccount(@Path("id") id: String): Call<AccountDto>

    @DELETE("/apps/accounts/{id}")
    fun removeAccount(@Path("id") id: String): Call<AccountDto>
}
