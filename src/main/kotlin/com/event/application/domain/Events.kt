package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Serdeable.Serializable
data class Events(
    val id: UUID,
    var title: String,
    var description: String? = null,
    var eventDate: LocalDate,
    var eventEndDate: LocalDate? = null,
    var totalTickets: Int,
    var availableTickets: Int,
    var ticketPrice: BigDecimal,
    var createdBy: String,
    var status: String,
)

enum class EventStatus {
    PUBLISHED,
    SOLD_OUT,
    CANCELLED,
}
