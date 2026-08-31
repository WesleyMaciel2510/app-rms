package com.example.rms.core.formatting

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateFormatter {
    private val brazilLocale = Locale("pt", "BR")
    private val shortDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", brazilLocale)
    private val mediumDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", brazilLocale)
    private val longDateFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", brazilLocale)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", brazilLocale)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", brazilLocale)

    fun formatShort(date: LocalDate): String = date.format(shortDateFormatter)
    fun formatShort(dateTime: LocalDateTime): String = dateTime.format(shortDateFormatter)

    fun formatMedium(date: LocalDate): String = date.format(mediumDateFormatter)
    fun formatMedium(dateTime: LocalDateTime): String = dateTime.format(mediumDateFormatter)

    fun formatLong(date: LocalDate): String = date.format(longDateFormatter)
    fun formatLong(dateTime: LocalDateTime): String = dateTime.format(longDateFormatter)

    fun formatDateTime(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormatter)

    fun formatTime(dateTime: LocalDateTime): String = dateTime.format(timeFormatter)

    fun formatRelative(date: LocalDate): String {
        val today = LocalDate.now()
        val daysDiff = ChronoUnit.DAYS.between(date, today)
        val diffLong = daysDiff
        
        return when {
            diffLong == 0L -> "Hoje"
            diffLong == 1L -> "Ontem"
            diffLong == -1L -> "Amanhã"
            diffLong > 0 && diffLong <= 7L -> "Há ${diffLong} dias"
            diffLong < 0 && diffLong >= -7L -> "Em ${-diffLong} dias"
            else -> formatShort(date)
        }
    }

    fun parseShort(dateString: String): LocalDate? {
        return try {
            LocalDate.parse(dateString, shortDateFormatter)
        } catch (e: java.time.format.DateTimeParseException) {
            null
        }
    }

    fun parseDateTime(dateString: String): LocalDateTime? {
        return try {
            LocalDateTime.parse(dateString, dateTimeFormatter)
        } catch (e: java.time.format.DateTimeParseException) {
            null
        }
    }
}