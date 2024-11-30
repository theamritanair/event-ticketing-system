package com.event.api

import com.event.api.service.EventsService
import com.event.application.constants.Constants.ADMIN_USERS
import com.event.application.domain.Events
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Singleton
@Controller
class EventsController(
    private val eventsService: EventsService
) : EventsAPI {

    val logger = LoggerFactory.getLogger(EventsController::class.java)

    override fun getAllEvents(): HttpResponse<List<Events>>{
        val events = eventsService.getAllEvents()
        logger.info("Total events found: ${events.size}")
        return HttpResponse.ok(events)
    }

    override fun getEventById(id: UUID): HttpResponse<Events> {
        val event = eventsService.getEventsByEventId(id)
        return if (event != null) {
            HttpResponse.ok(event)
        } else {
            HttpResponse.notFound()
        }
    }

    override fun getEventsByName(name: String, page: Int): HttpResponse<List<Events>> {
        val events = eventsService.searchEventsByName(name, page)
        return HttpResponse.ok(events.content)
    }

    override fun createEvent(
        name: String,
        description: String,
        date: String,
        totalTickets: Int,
        availableTickets: Int,
        ticketPrice: BigDecimal,
        createdBy: String
    ): HttpResponse<*> {
        //Pre validations
        if (!ADMIN_USERS.contains(createdBy)) {
            return HttpResponse.badRequest("Error: User is not authorized to create events.")
        }
        if (availableTickets > totalTickets) {
            return HttpResponse.badRequest("Error: Available tickets cannot exceed total tickets.")
        }
        //Date should be parseable
        try {
            LocalDate.parse(date)
        } catch (e: Exception) {
            return HttpResponse.badRequest("Error: Invalid date format. Please use yyyy-MM-dd")
        }
        //Validate ticket price
        if (ticketPrice <= BigDecimal.ZERO) {
            return HttpResponse.badRequest("Error: Ticket price should be greater than zero.")
        }

        val result = eventsService.createEvent(name, description, date, totalTickets, availableTickets, ticketPrice, createdBy)
        return if (result.isSuccess) {
            logger.info("Event created successfully.")
            HttpResponse.created(result.getOrNull())
        } else {
            logger.error("Error creating event: ${result.exceptionOrNull()?.message}")
            HttpResponse.serverError("Error creating event.")
        }
    }

}