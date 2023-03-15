package io.snabble.pay.app.data.repository.account.label

import io.snabble.pay.app.data.accountlabel.AccountLabelDao
import io.snabble.pay.app.data.accountlabel.model.AccountLabel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalAccountLabelDataSource {

    fun getAllLabels(): Flow<List<AccountLabel>>

    suspend fun setLabel(id: String, label: String)

    suspend fun deleteOrphanedLabels(accountIds: List<String>)
}

class LocalAccountLabelDataSourceImpl @Inject constructor(
    private val labelDao: AccountLabelDao,
) : LocalAccountLabelDataSource {

    override fun getAllLabels(): Flow<List<AccountLabel>> = labelDao.getAllLabels()

    override suspend fun setLabel(id: String, label: String) =
        labelDao.setLabel(AccountLabel(accountId = id, label = label))

    override suspend fun deleteOrphanedLabels(accountIds: List<String>) =
        labelDao.deleteAllOrphanedLabels(accountIds)
}
