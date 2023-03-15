package io.snabble.pay.app.data.accountlabel

import androidx.room.Database
import androidx.room.RoomDatabase
import io.snabble.pay.app.data.accountlabel.model.AccountLabel

@Database(entities = [AccountLabel::class], version = 1, exportSchema = false)
abstract class AccountLabelDatabase : RoomDatabase() {

    abstract fun accountLabelDao(): AccountLabelDao

    companion object {

        const val DB_NAME = "account_label_database"
    }
}
