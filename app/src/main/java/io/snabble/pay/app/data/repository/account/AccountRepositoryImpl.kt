package io.snabble.pay.app.data.repository.account

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.data.accountlabel.model.AccountLabel
import io.snabble.pay.app.data.repository.account.label.LocalAccountLabelDataSource
import io.snabble.pay.app.data.repository.account.remotedatasource.AccountRemoteDataSource
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.toErrorResponse
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.AccountRepository
import io.snabble.pay.app.domain.account.toAccountCard
import io.snabble.pay.core.util.Failure
import io.snabble.pay.core.util.Success
import io.snabble.pay.shared.account.domain.model.Account
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
    ): AppResult<AccountCheck> =
        when (val result = remoteDataSource.addNewAccount(appUri, city, twoLetterIsoCountryCode)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(result.value)
        }

    override suspend fun deleteAccount(id: String): AppResult<AccountCard> {
        val label = localAccountLabelSource.getAllLabels()
            .firstOrNull()
            ?.find { it.accountId == id }
        return when (val result = remoteDataSource.deleteAccount(id)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(
                result.value.toAccountCard(
                    savedName = label?.name,
                    colors = label?.colors
                )
            )
        }
    }

    override fun getAccounts(): Flow<AppResult<List<AccountCard>>> = channelFlow {
        when (val result = remoteDataSource.getAllAccounts()) {
            is Failure -> send(AppError(result.error.toErrorResponse()))
            is Success -> {
                localAccountLabelSource.deleteOrphanedLabels(result.value.map(Account::id))
                localAccountLabelSource.getAllLabels()
                    .collectLatest { labels ->
                        send(AppSuccess(result.value.mapLabels(labels)))
                    }
            }
        }
    }

    private fun List<Account>.mapLabels(labels: List<AccountLabel>) =
        map { account ->
            val label = labels
                .find { it.accountId == account.id }
            account.toAccountCard(
                savedName = label?.name,
                colors = label?.colors
            )
        }

    override suspend fun setAccountLabel(id: String, label: String, colors: List<String>) {
        localAccountLabelSource.setLabel(
            id = id,
            label = label,
            colors = colors
        )
    }

    override suspend fun getAccount(id: String): AppResult<AccountCard> {
        val label = localAccountLabelSource.getAllLabels()
            .firstOrNull()
            ?.find { it.accountId == id }
        return when (val result = remoteDataSource.getAccount(id)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(
                result.value.toAccountCard(
                    savedName = label?.name,
                    colors = label?.colors
                )
            )
        }
    }
}
