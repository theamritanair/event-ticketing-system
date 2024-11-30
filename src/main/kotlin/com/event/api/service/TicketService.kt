package com.event.api.service

import com.event.application.exception.EventNotFoundException
import com.event.application.domain.Ticket
import com.event.application.exception.InsufficientWalletBalance
import com.event.application.exception.TicketSoldOutException
import com.event.application.exception.UserNotFoundException
import com.event.datasource.mapper.toTicket
import com.event.datasource.mapper.toTicketEntity
import com.event.datasource.repository.EventsRepository
import com.event.datasource.repository.TicketRepository
import com.event.datasource.repository.UserRepository
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.*

@Singleton
class TicketService(
    private val eventsRepository: EventsRepository,
    private val ticketRepository: TicketRepository,
    private val userRepository: UserRepository
) {

    fun purchaseTicket(eventId: UUID, username: String): Result<Ticket> {
        val event = eventsRepository.findById(eventId).orElse(null)
            ?: throw EventNotFoundException("Event not found for id $eventId")

        val user = userRepository.findByUsernameIgnoreCase(username) ?: throw UserNotFoundException("User not found for id $username")

        if (event.availableTickets <= 0) {
            throw TicketSoldOutException("Tickets are sold out for event $eventId")
        }
        if(user.walletBalance<event.ticketPrice){
            throw InsufficientWalletBalance("Insufficient balance")
        }

        val ticket = Ticket(
            transactionId = UUID.randomUUID(),
            eventId = eventId,
            userId = user.id!!,
            purchaseDate = LocalDate.now()
        )
        user.walletBalance-=event.ticketPrice
        event.availableTickets--
        eventsRepository.update(event)
        ticketRepository.save(ticket.toTicketEntity())
        return Result.success(ticket)
    }

    fun getTicketsByEventId(eventId: UUID): List<Ticket> {
        val tickets = ticketRepository.findByEventId(eventId)
        return tickets.map { it.toTicket() }
    }
}