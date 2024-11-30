package com.event.api.service

import com.event.application.exception.EventNotFoundException
import com.event.application.domain.Ticket
import com.event.application.exception.TicketSoldOutException
import com.event.datasource.mapper.toTicketEntity
import com.event.datasource.repository.EventsRepository
import com.event.datasource.repository.TicketRepository
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.*

@Singleton
class TicketService(
    private val eventsRepository: EventsRepository,
    private val ticketRepository: TicketRepository
) {

    fun purchaseTicket(eventId: UUID, userId: String): Result<Ticket> {
        val event = eventsRepository.findById(eventId).orElse(null)
            ?: throw EventNotFoundException("Event not found for id $eventId")

        if (event.availableTickets <= 0) {
            throw TicketSoldOutException("Tickets are sold out for event $eventId")
        }
        val ticket = Ticket(
            transactionId = UUID.randomUUID(),
            eventId = eventId,
            userId = userId,
            purchaseDate = LocalDate.now()
        )
        event.availableTickets--;
        eventsRepository.update(event)
        ticketRepository.save(ticket.toTicketEntity())
        return Result.success(ticket)
    }
}