package io.snabble.pay.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.snabble.pay.account.domain.model.MandateState
import java.time.ZonedDateTime

@Entity
data class AccountCard(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "bank") val bank: String,
    @ColumnInfo(name = "created_at") val createdAt: ZonedDateTime,
    @ColumnInfo(name = "currency_code") val currencyCode: String,
    @ColumnInfo(name = "holder_name") val holderName: String,
    @ColumnInfo(name = "iban") val iban: String,
    @ColumnInfo(name = "mandate_state") val mandateState: MandateState,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "colors") val colors: List<String>,
)
