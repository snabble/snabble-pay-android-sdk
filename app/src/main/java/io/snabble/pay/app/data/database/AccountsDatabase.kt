package io.snabble.pay.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.snabble.pay.app.data.dao.AccountDao
import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.data.entity.ZonedDateTimeConverter

@Database(entities = [AccountCard::class], version = 1, exportSchema = false)
@TypeConverters(ZonedDateTimeConverter::class)
abstract class AccountsDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {

        const val DB_NAME = "AccountsDatabase"
    }
}

