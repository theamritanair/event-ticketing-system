package com.event.datasource.entity

import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "events")
data class EventsEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = true)
    var description: String? = null,

    @Column(name = "event_date", nullable = false)
    var eventDate: LocalDate,

    @Column(name = "total_tickets", nullable = false)
    var totalTickets: Int,

    @Column(name = "available_tickets", nullable = false)
    var availableTickets: Int,

    @Column(name = "ticket_price", nullable = false)
    var ticketPrice: BigDecimal,

    @Column(name = "created_by", nullable = false)
    var createdBy: String
)