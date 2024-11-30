package com.event.api.service

import com.event.application.domain.Events
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
    private var eventsRepository: EventsRepository
) {

     fun getAllEvents()  : List<Events>{
        val result = eventsRepository.findAll().map { it.toEvents() }
        return result;
     }

    fun getEventsByEventId(id: UUID): Events? {
        val event = eventsRepository.findById(id).orElse(null)?.toEvents()
        return event
    }

    fun searchEventsByName(
        name: String,
        page: Int = 0,
        size: Int = 10
    ): Page<Events> {
        val pageable = Pageable.from(page, size)
        return eventsRepository.findByTitleContainingIgnoreCase(
            name,
            pageable
        ).map{ it.toEvents() }
    }

    fun createEvent(
        name: String,
        description: String,
        date: String,
        totalTickets: Int,
        availableTickets: Int,
        ticketPrice: BigDecimal,
        createdBy: String
    ): Result<Events> {
        val event = Events(
            id = UUID.randomUUID(),
            title = name,
            description = description,
            eventDate = LocalDate.parse(date),
            totalTickets = totalTickets,
            availableTickets = availableTickets,
            ticketPrice = ticketPrice,
            createdBy = createdBy
        )
        val newEvent = eventsRepository.save(event.toEventsEntity())
        return Result.success(newEvent.toEvents())
    }

    fun isDuplicateEvent(name: String, date: LocalDate): Boolean {
        return eventsRepository.existsByTitleAndEventDate(name, date)
    }

}