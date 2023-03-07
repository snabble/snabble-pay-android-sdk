package io.snabble.pay.app.data.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
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
)

object LocalDateTimeConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter fun toDate(dateString: String?): ZonedDateTime? {
        return if (dateString == null) {
            null
        } else {
            ZonedDateTime.parse(dateString)
        }
    }

    @TypeConverter fun toDateString(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}
