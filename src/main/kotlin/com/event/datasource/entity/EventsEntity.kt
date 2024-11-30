package com.event.datasource.entity

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "events")
data class EventsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,
    @Column(name = "title", nullable = false)
    var title: String,
    @Column(name = "description", nullable = true)
    var description: String? = null,
    @Column(name = "event_start_date", nullable = false)
    var eventStartDate: LocalDate,
    @Column(name = "event_end_date", nullable = true)
    var eventEndDate: LocalDate? = null,
    @Column(name = "total_tickets", nullable = false)
    var totalTickets: Int,
    @Column(name = "available_tickets", nullable = false)
    var availableTickets: Int,
    @Column(name = "ticket_price", nullable = false)
    var ticketPrice: BigDecimal,
    @Column(name = "status", nullable = false)
    var status: String,
    @Column(name = "created_by", nullable = false)
    var createdBy: String,
    @DateCreated
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @DateUpdated
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)
