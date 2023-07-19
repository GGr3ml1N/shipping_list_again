package com.ggr3ml1n.shoppinglist.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

object TimeManager {
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}