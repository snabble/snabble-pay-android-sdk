package io.snabble.pay.app.data.repository.account

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.data.repository.account.label.LocalAccountLabelDataSource
import io.snabble.pay.app.data.repository.account.remotedatasource.AccountRemoteDataSource
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.AccountRepository
import io.snabble.pay.app.domain.account.toAccountCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource,
    private val localAccountLabelSource: LocalAccountLabelDataSource,
) : AccountRepository {

    override suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> =
        remoteDataSource.addNewAccount(appUri, city, twoLetterIsoCountryCode)

    override suspend fun deleteAccount(id: String): AccountCard {
        val label = localAccountLabelSource.getAllLabels()
            .firstOrNull()
            ?.find { it.accountId == id }
        return remoteDataSource.deleteAccount(id)
            .getOrThrow()
            .toAccountCard(
                savedName = label?.name,
                colors = label?.colors
            )
    }

    override fun getAccounts(): Flow<List<AccountCard>> = channelFlow {
        remoteDataSource.getAllAccounts()
            .onFailure { throw it }
            .onSuccess { accounts ->
                localAccountLabelSource.deleteOrphanedLabels(accounts.map(Account::id))

                localAccountLabelSource.getAllLabels()
                    .collectLatest { labels ->
                        val accountList: List<AccountCard> = accounts
                            .map { account ->
                                val label = labels
                                    .find { it.accountId == account.id }
                                account.toAccountCard(
                                    savedName = label?.name,
                                    colors = label?.colors
                                )
                            }
                        send(accountList)
                    }
            }
    }

    override suspend fun setAccountLabel(id: String, label: String, colors: List<String>) {
        localAccountLabelSource.setLabel(
            id = id,
            label = label,
            colors = colors
        )
    }

    override suspend fun getAccount(id: String): AccountCard {
        val label = localAccountLabelSource.getAllLabels()
            .firstOrNull()
            ?.find { it.accountId == id }
        return remoteDataSource.getAccount(id)
            .getOrThrow()
            .toAccountCard(
                savedName = label?.name,
                colors = label?.colors
            )
    }
}
