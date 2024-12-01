package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Serdeable.Serializable
data class Ticket(
    val transactionId: UUID,
    val eventId: UUID,
    val userId: Long,
    val quantity: Int,
    val totalPrice: BigDecimal,
    val purchaseDateTime: LocalDateTime,
)
