package io.snabble.pay.app.data.database.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.ZonedDateTime

object ZonedDateTimeConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter fun toDate(dateString: String?): ZonedDateTime? {
        dateString ?: return null
        return ZonedDateTime.parse(dateString)
    }

    @TypeConverter fun toDateString(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}

object ListStringConverter {

    @TypeConverter fun toList(string: String?): List<String>? {
        string ?: return null
        return string.split(";")
    }

    @TypeConverter fun toString(stringList: List<String>?): String? {
        return stringList?.joinToString(";")
    }
}
