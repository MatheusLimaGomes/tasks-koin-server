package br.com.tasks.utils.extensions

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

//TODO: Review Formatter
fun LocalDateTime.toDateString(): String = dateFormat.format(this)
private val zoneId: ZoneId = ZoneId.of("America/Sao_Paulo")

val dateFormat: DateTimeFormatter = DateTimeFormatter
    .ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
    .withLocale(Locale.of("pt", "br"))
    .withZone(zoneId)