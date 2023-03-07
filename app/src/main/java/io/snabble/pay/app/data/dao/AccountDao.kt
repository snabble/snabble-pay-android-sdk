package io.snabble.pay.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.snabble.pay.app.data.entity.AccountCard

@Dao
interface AccountDao {

    @Query("SELECT * FROM accountcard")
    suspend fun getAllAccounts(): List<AccountCard>

    @Query("SELECT * FROM accountcard WHERE id LIKE:id")
    suspend fun getAccountById(id: String): AccountCard

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAccounts(accounts: List<AccountCard>)

    @Query("DELETE FROM accountcard")
    suspend fun clearAccounts()

    @Delete
    suspend fun deleteAccount(account: AccountCard)
}
