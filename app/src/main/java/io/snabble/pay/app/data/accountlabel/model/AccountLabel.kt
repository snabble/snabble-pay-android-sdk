package io.snabble.pay.app.data.accountlabel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_label_table")
data class AccountLabel(
    @PrimaryKey @ColumnInfo(name = "account_id") val accountId: String,
    @ColumnInfo(name = "label") val name: String,
    @ColumnInfo(name = "colors") val colors: List<String>,
)
