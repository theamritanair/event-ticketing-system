package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Serdeable.Serializable
data class Events (
    val id: UUID,
    val title: String,
    val description: String? = null,
    val eventDate: LocalDate,
    val totalTickets: Int,
    val availableTickets: Int,
    val ticketPrice: BigDecimal,
    val createdBy: String,
)