package io.snabble.pay.app.data.database.utils

import androidx.room.TypeConverter

object ListStringConverter {

    @TypeConverter fun toList(string: String?): List<String>? {
        string ?: return null
        return string.split(";")
    }

    @TypeConverter fun toString(stringList: List<String>?): String? {
        return stringList?.joinToString(";")
    }
}
