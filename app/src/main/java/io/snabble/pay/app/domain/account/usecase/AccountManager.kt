package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.domain.account.AccountCardModel
import javax.inject.Inject

interface AccountManager {

    suspend fun getAccountModels(): List<AccountCardModel>

    suspend fun getAccountModel(accountId: String): AccountCardModel

    suspend fun updateAccountName(accountId: String, name: String)
}

class AccountManagerImpl @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountNameUseCase: UpdateAccountNameUseCase,
) : AccountManager {

    override suspend fun getAccountModels(): List<AccountCardModel> =
        getAccountsUseCase()

    override suspend fun getAccountModel(accountId: String): AccountCardModel =
        getAccountUseCase(accountId)

    override suspend fun updateAccountName(accountId: String, name: String) =
        updateAccountNameUseCase(accountId, name)
}
