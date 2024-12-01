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
        totalTickets = totalTickets,
        availableTickets = availableTickets,
        ticketPrice = ticketPrice,
        createdBy = createdBy,
        status = status,
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
        createdBy = createdBy,
        status = status,
    )

fun Ticket.toTicketEntity(): TicketEntity =
    TicketEntity(
        userId = userId,
        eventId = eventId,
        transactionId = transactionId,
        quantity = quantity,
        totalPrice = totalPrice,
    )

fun TicketEntity.toTicket(): Ticket =
    Ticket(
        userId = userId,
        eventId = eventId,
        transactionId = transactionId,
        quantity = quantity,
        totalPrice = totalPrice,
        purchaseDateTime = purchaseDateTime,
    )
