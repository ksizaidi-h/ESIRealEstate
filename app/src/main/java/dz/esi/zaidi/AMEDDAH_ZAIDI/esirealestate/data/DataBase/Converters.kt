package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase

import android.text.TextUtils
import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String {
        if (list.size == 0) {
            return ""
        }

        val builder = StringBuilder(list[0])
        for (i in 1..list.size - 1) {
            builder.append(",").append(list[i])
        }
        return builder.toString()
    }

    @TypeConverter
    fun StringToList(value: String): List<String>? {
        return if (TextUtils.isEmpty(value)) null else value.split(",").toList()
    }
}