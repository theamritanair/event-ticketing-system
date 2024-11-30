package com.event.api

import com.event.api.service.EventsService
import com.event.application.domain.Events
import com.event.utility.Helper
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.LocalDate

import java.util.*

@Singleton
@Controller
class EventsController(
    private val eventsService: EventsService,
    private val logger : Logger = LoggerFactory.getLogger(EventsController::class.java)
) : EventsAPI {

    override fun getAllEvents(): HttpResponse<List<Events>>{
        val events = eventsService.getAllEvents()
        logger.info("Total events found: ${events.size}")
        return HttpResponse.ok(events)
    }

    override fun getEventById(id: UUID): HttpResponse<Events> {
        logger.info("Received request to get event by id: $id")
        val event = eventsService.getEventsByEventId(id)
        return if (event != null) {
            logger.debug("Event found: {}", event)
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
        logger.info("Received request to create event: $name")
        //Pre validations
        val validationResponse = Helper.validateCreateEventRequest(date, totalTickets, availableTickets, ticketPrice, createdBy);
        if (validationResponse != null) {
            logger.error("Error in create event request: $validationResponse")
            return HttpResponse.badRequest(validationResponse)
        }

        if (eventsService.isDuplicateEvent(name, LocalDate.parse(date))) {
            logger.error("Duplicate event found: $name on $date")
            return HttpResponse.badRequest("Error: Duplicate event found for the name and event date.")
        }
        val result = eventsService.createEvent(name, description, date, totalTickets, availableTickets, ticketPrice, createdBy)
        return if (result.isSuccess) {
            logger.info("Event created successfully!")
            HttpResponse.created(result.getOrNull())
        } else {
            logger.error("Error creating event: ${result.exceptionOrNull()?.message}")
            HttpResponse.serverError("Error creating event.")
        }
    }

}