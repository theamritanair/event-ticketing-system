package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.util.*
@Serdeable.Serializable
class EventsStats(
    val eventId: UUID,
    val eventName: String,
    val availableTickets: Int,
    val ticketsSold: Int,
    val ticketsRevenue: BigDecimal
)