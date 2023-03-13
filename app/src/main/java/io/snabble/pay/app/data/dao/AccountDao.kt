package io.snabble.pay.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.entity.AccountCard

@Dao
interface AccountDao {

    @Query("SELECT * FROM accountcard")
    suspend fun getAllAccounts(): List<AccountCard>

    @Query("SELECT * FROM accountcard WHERE id LIKE:id")
    suspend fun getAccountById(id: String): AccountCard

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAccounts(accounts: List<AccountCard>)

    @Query("UPDATE accountcard SET mandate_state = :mandateState WHERE id LIKE:id")
    suspend fun updateAccount(id: String, mandateState: MandateState)

    @Query("DELETE FROM accountcard")
    suspend fun clearAccounts()

    @Delete
    suspend fun deleteAccount(account: AccountCard)

    @Query("UPDATE accountcard SET name = :name WHERE id LIKE:id")
    suspend fun updateAccountName(id: String, name: String)
}
