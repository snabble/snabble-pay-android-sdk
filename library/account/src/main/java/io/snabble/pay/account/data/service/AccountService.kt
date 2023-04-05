package io.snabble.pay.account.data.service

import io.snabble.pay.account.data.dto.AccountCheckDto
import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.shared.account.data.dto.AccountDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Service for account related interactions of the Snabble Pay API.
 */
internal interface AccountService {

    /**
     * Creates a new account check object with a link to an account check service provider
     * to add an account to Snabble Pay
     *
     * [Docs to add an account](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#Add-a-new-Account)
     */
    @GET("/apps/accounts/check")
    suspend fun getAccountCheck(
        @Query("appUri") appUri: String,
        @Query("city") city: String,
        @Query("countryCode") twoLetterIsoCountryCode: String,
    ): ApiResponse<AccountCheckDto>

    /**
     * Returns a list of all accounts associate with this Snabble Pay instance.
     * The list is empty if there are no accounts.
     *
     * [Docs to get all accounts](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#Get-a-specific-account)
     */
    @GET("/apps/accounts")
    suspend fun getAccounts(): ApiResponse<List<AccountDto>>

    /**
     * With an account id the specific account is fetched.
     * If the user has insufficient rights to access the specified account or the account does not
     * exist a HTTP 404 Not Found is returned.
     *
     * [Docs to get a specifig account](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#Get-a-specific-account)
     */
    @GET("/apps/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): ApiResponse<AccountDto>

    /**
     * An account can be removed with the account's id.
     * If the user has insufficient rights to delete the specified account or the account does not
     * exist a HTTP 404 Not Found is returned.
     *
     * [Docs to delete an account](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#Delete-an-account)
     */
    @DELETE("/apps/accounts/{id}")
    suspend fun removeAccount(@Path("id") id: String): ApiResponse<AccountDto>
}
