package dev.seabat.android.kotlinwebsocket.utils

import android.app.admin.DevicePolicyResourcesManager
import android.icu.text.DateFormat
import android.icu.text.DateFormatSymbols
import android.icu.util.JapaneseCalendar
import android.os.Build
import android.os.Bundle
import java.util.Date
import java.util.Locale

fun convertToJapaneseCalender(date: Date): String {
    val calendar: android.icu.util.Calendar = JapaneseCalendar()
    val df: DateFormat = android.icu.text.SimpleDateFormat(
        "Gy年M月d日",
        DateFormatSymbols(calendar, Locale.JAPANESE)
    )
    df.calendar = calendar
    return df.format(date)
}

fun getDateFromBundle(bundle: Bundle, key: String): Date {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        bundle.getSerializable(key, java.util.Date::class.java) ?: Date()
    } else {
        @Suppress("DEPRECATION")
        bundle.getSerializable(key) as? Date ?: Date()
    }
}
