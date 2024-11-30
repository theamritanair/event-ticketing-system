package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
@Serdeable.Serializable
data class Ticket (
    val transactionId: UUID,
    val eventId: UUID,
    val userId: String,
    val quantity: Int,
    val totalPrice: BigDecimal,
    val purchaseDateTime: LocalDateTime
)