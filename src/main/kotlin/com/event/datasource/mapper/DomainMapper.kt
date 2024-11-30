package com.event.datasource.mapper

import com.event.application.domain.Events
import com.event.application.domain.Ticket
import com.event.datasource.entity.EventsEntity
import com.event.datasource.entity.TicketEntity


fun Events.toEventsEntity(): EventsEntity =
     EventsEntity(
        title = title,
        description = description,
        eventDate = eventDate,
        totalTickets = this.totalTickets,
        availableTickets = this.availableTickets,
        ticketPrice = ticketPrice,
        createdBy = this.createdBy
    )

fun EventsEntity.toEvents(): Events =
    Events(
        id = id!!,
        title = title,
        description = description,
        eventDate = eventDate,
        totalTickets = totalTickets,
        availableTickets = availableTickets,
        ticketPrice = ticketPrice,
        createdBy = createdBy
    )

fun Ticket.toTicketEntity(): TicketEntity =
    TicketEntity(
        userId = userId,
        eventId = eventId,
        transactionId = transactionId,
        purchaseDate = purchaseDate
    )
