package com.newsapp.core.utils

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.security.MessageDigest
import java.util.Locale

object Helper {
    fun md5orBlank(input: String?): String {
        if (input == null) return ""
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

    fun timeConverter(input: String?): String {
        if (input == null) return ""
        val instant = Instant.parse(input)
        val zoneId = ZoneId.of("Asia/Jakarta")
        val localDateTime = instant.atZone(zoneId)

        // Format to "EEE, dd MMM HH.mm" in Indonesian locale
        val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM HH.mm", Locale("id", "ID"))
        val output = localDateTime.format(formatter)
        return output
    }
}