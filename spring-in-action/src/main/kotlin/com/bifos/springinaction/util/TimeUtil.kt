package com.bifos.springinaction.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtil {

    fun LocalDate.parseLong(): Long {
        return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun LocalDateTime.parseLong(): Long {
        return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun Long.parseLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun Long.parseLocalDateTime(): LocalDateTime {
        return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}