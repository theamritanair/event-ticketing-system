package com.event.api.service

import com.event.application.domain.EventStatus
import com.event.application.domain.Events
import com.event.application.domain.EventsStats
import com.event.application.exception.EventNotFoundException
import com.event.datasource.mapper.toEvents
import com.event.datasource.mapper.toEventsEntity
import com.event.datasource.repository.EventsRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Singleton
class EventsService(
    private var eventsRepository: EventsRepository,
) {
    fun getAllEvents(): List<Events> {
        val result = eventsRepository.findAll().map { it.toEvents() }
        return result
    }

    fun getEventsByEventId(id: UUID): Events? {
        val event = eventsRepository.findById(id).orElse(null)?.toEvents()
        return event
    }

    fun searchEventsByName(
        name: String,
        page: Int = 0,
        size: Int = 10,
    ): Page<Events> {
        val pageable = Pageable.from(page, size)
        return eventsRepository.findByTitleContainingIgnoreCase(
            name,
            pageable,
        ).map { it.toEvents() }
    }

    fun createEvent(
        name: String,
        description: String,
        eventStartDate: String,
        eventEndDate: String?,
        totalTickets: Int,
        availableTickets: Int,
        ticketPrice: BigDecimal,
        createdBy: String,
    ): Result<Events> {
        val event =
            Events(
                id = UUID.randomUUID(),
                title = name,
                description = description,
                eventStartDate = LocalDate.parse(eventStartDate),
                eventEndDate = eventEndDate?.let { LocalDate.parse(it) },
                totalTickets = totalTickets,
                availableTickets = availableTickets,
                ticketPrice = ticketPrice,
                createdBy = createdBy,
                status = EventStatus.PUBLISHED.name,
            )
        val newEvent = eventsRepository.save(event.toEventsEntity())
        return Result.success(newEvent.toEvents())
    }

    fun isDuplicateEvent(
        name: String,
        eventStartDate: String,
        eventEndDate: String?,
    ): Boolean {
        return if (eventEndDate == null) {
            eventsRepository.existsByTitleAndEventStartDate(name, LocalDate.parse(eventStartDate))
        } else {
            eventsRepository.existsByTitleAndEventStartDateAndEventEndDate(
                name,
                LocalDate.parse(eventStartDate),
                LocalDate.parse(eventEndDate),
            )
        }
    }

    fun getEventsStats(eventId: UUID): EventsStats {
        val event = eventsRepository.findById(eventId).orElse(null)?.toEvents()
        if (event != null) {
            val ticketsSold = event.totalTickets - event.availableTickets
            return EventsStats(
                eventId = event.id,
                eventName = event.title,
                availableTickets = event.availableTickets,
                ticketsSold = ticketsSold,
                ticketsRevenue = event.ticketPrice * ticketsSold.toBigDecimal(),
                status = event.status,
            )
        } else {
            throw EventNotFoundException("Event not found for id $eventId")
        }
    }
}
