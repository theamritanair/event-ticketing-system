package com.event.datasource.mapper

import com.event.application.domain.Events
import com.event.application.domain.Ticket
import com.event.application.domain.User
import com.event.datasource.entity.EventsEntity
import com.event.datasource.entity.TicketEntity
import com.event.datasource.entity.UserEntity

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

fun User.toUserEntity(): UserEntity =
    UserEntity(
        name = name,
        email = email,
        username = username,
        walletBalance = walletBalance,
        role = role,
    )

fun UserEntity.toUser(): User =
    User(
        username = username,
        name = name,
        email = email,
        walletBalance = walletBalance,
        role = role,
    )
