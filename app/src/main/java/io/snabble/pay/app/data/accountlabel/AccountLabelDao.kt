package io.snabble.pay.app.data.accountlabel

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.snabble.pay.app.data.accountlabel.model.AccountLabel
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountLabelDao {

    @Query("SELECT * FROM account_label_table")
    fun getAllLabels(): Flow<List<AccountLabel>>

    @Upsert(entity = AccountLabel::class)
    suspend fun setLabel(label: AccountLabel)

    @Query("DELETE FROM account_label_table WHERE account_id not in (:accountIds)")
    suspend fun deleteAllOrphanedLabels(accountIds: List<String>)
}
