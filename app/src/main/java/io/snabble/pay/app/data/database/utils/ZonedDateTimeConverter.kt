package io.snabble.pay.app.data.database.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.ZonedDateTime

object ZonedDateTimeConverter {

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
