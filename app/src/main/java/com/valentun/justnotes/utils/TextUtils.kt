package com.valentun.justnotes.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(millis: Long): String {
    val date = Date(millis)
    return SimpleDateFormat.getDateTimeInstance().format(date)
}

fun String.clip(size: Int) =
        if (this.length <= size) {
            this
        } else {
            this.substring(0, size - 1)
        }