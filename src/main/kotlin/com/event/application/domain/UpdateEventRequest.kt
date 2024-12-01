package com.event.application.domain

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal

@Introspected
@Serdeable.Deserializable
data class UpdateEventRequest(
    val name: String?,
    val description: String?,
    val eventDate: String?,
    val totalTickets: Int?,
    val availableTickets: Int?,
    val ticketPrice: BigDecimal?,
    val status: String?,
)
