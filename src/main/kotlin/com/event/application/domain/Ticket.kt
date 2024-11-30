package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate
import java.util.*
@Serdeable.Serializable
data class Ticket (
    val transactionId: UUID,
    val eventId: UUID,
    val userId: String,
    val purchaseDate: LocalDate,
)