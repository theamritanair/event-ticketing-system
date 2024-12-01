package com.event.api.service

import com.event.application.constants.Constants.UNAUTHORIZED_USER_ERROR
import com.event.application.domain.EventStatus
import com.event.application.domain.Ticket
import com.event.application.exception.EventNotFoundException
import com.event.application.exception.InsufficientWalletBalance
import com.event.application.exception.TicketSoldOutException
import com.event.application.exception.UnauthorizedUserException
import com.event.application.exception.UserNotFoundException
import com.event.datasource.entity.EventsEntity
import com.event.datasource.mapper.toTicket
import com.event.datasource.mapper.toTicketEntity
import com.event.datasource.repository.EventsRepository
import com.event.datasource.repository.TicketRepository
import com.event.datasource.repository.UserRepository
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Singleton
open class TicketService(
    private val eventsRepository: EventsRepository,
    private val ticketRepository: TicketRepository,
    private val userRepository: UserRepository,
    private val entityManager: EntityManager,
) {
    @Transactional
    open fun purchaseTicket(
        eventId: UUID,
        eventDate: String,
        username: String,
        quantity: Int,
    ): Result<Ticket> {
        val eventExists =
            eventsRepository.existsByIdAndEventDate(eventId, LocalDate.parse(eventDate))

        if (!eventExists) {
            throw EventNotFoundException("Event not found for id $eventId and date $eventDate")
        }
        val user = userRepository.findByUsernameIgnoreCase(username) ?: throw UserNotFoundException("User not found for id $username")

        val query =
            entityManager.createQuery(
                "SELECT e FROM EventsEntity e WHERE e.id = :eventId AND e.eventDate = :eventDate",
                EventsEntity::class.java,
            )
        query.setParameter("eventId", eventId)
        query.setParameter("eventDate", LocalDate.parse(eventDate))
        query.setLockMode(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
        val event = query.singleResult

        if (event.availableTickets <= 0 || event.status == EventStatus.SOLD_OUT.name) {
            throw TicketSoldOutException("Tickets are sold out for event $eventId")
        }
        if (event.availableTickets < quantity) {
            throw TicketSoldOutException("$quantity tickets are not available for event $eventId")
        }
        if (user.walletBalance < event.ticketPrice) {
            throw InsufficientWalletBalance("Insufficient balance")
        }
        val totalPrice: BigDecimal = event.ticketPrice.times(quantity.toBigDecimal())
        val ticket =
            Ticket(
                transactionId = UUID.randomUUID(),
                eventId = eventId,
                userId = user.id!!,
                purchaseDateTime = LocalDateTime.now(),
                quantity = quantity,
                totalPrice = totalPrice,
            )
        // Deduct ticket price from user wallet balance
        user.walletBalance -= event.ticketPrice

        // Decrease available tickets
        event.availableTickets--

        // If available tickets are 0, then set status to SOLD_OUT
        if (event.availableTickets == 0) {
            event.status = EventStatus.SOLD_OUT.name
        }

        userRepository.update(user)
        eventsRepository.update(event)
        ticketRepository.save(ticket.toTicketEntity())
        return Result.success(ticket)
    }

    fun getTicketsByEventId(
        eventId: UUID,
        username: String,
    ): List<Ticket> {
        if (userRepository.findByUsernameIgnoreCase(username)?.role != "ADMIN") {
            throw UnauthorizedUserException(UNAUTHORIZED_USER_ERROR)
        }
        val eventExists =
            eventsRepository.existsById(eventId)

        if (!eventExists) {
            throw EventNotFoundException("Event not found for id $eventId")
        }
        val tickets = ticketRepository.findByEventId(eventId)
        return tickets.map { it.toTicket() }
    }
}
