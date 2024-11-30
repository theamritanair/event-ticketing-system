package com.event.api.http

import com.event.api.service.EventsService
import com.event.api.service.TicketService
import com.event.application.domain.Events
import com.event.application.domain.EventsStats
import com.event.application.domain.Ticket
import com.event.application.exception.EventNotFoundException
import com.event.application.exception.InsufficientWalletBalance
import com.event.application.exception.TicketSoldOutException
import com.event.application.exception.UserNotFoundException
import com.event.utility.Helper
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.QueryValue
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.UUID

@Singleton
@Controller
class EventsController(
    private val eventsService: EventsService,
    private val ticketService: TicketService,
    private val logger: Logger = LoggerFactory.getLogger(EventsController::class.java),
) : EventsAPI {
    override fun getAllEvents(): HttpResponse<List<Events>>  {
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

    override fun getEventsByName(
        name: String,
        page: Int,
    ): HttpResponse<List<Events>> {
        val events = eventsService.searchEventsByName(name, page)
        return HttpResponse.ok(events.content)
    }

    override fun createEvent(
        name: String,
        description: String,
        eventStartDate: String,
        eventEndDate: String?,
        totalTickets: Int,
        availableTickets: Int,
        ticketPrice: BigDecimal,
        createdBy: String,
    ): HttpResponse<*> {
        logger.info("Received request to create event: $name")
        // Pre validations
        val validationResponse =
            Helper.validateCreateEventRequest(
                eventStartDate,
                eventEndDate,
                totalTickets,
                availableTickets,
                ticketPrice,
                createdBy,
            )
        if (validationResponse != null) {
            logger.error("Error in create event request: $validationResponse")
            return HttpResponse.badRequest(validationResponse)
        }

        if (eventsService.isDuplicateEvent(name, eventStartDate, eventEndDate)) {
            logger.error("Duplicate event found: $name on $eventStartDate to $eventEndDate")
            return HttpResponse.badRequest("Error: Duplicate event found for the name and event dates.")
        }
        val result =
            eventsService.createEvent(
                name,
                description,
                eventStartDate,
                eventEndDate,
                totalTickets,
                availableTickets,
                ticketPrice,
                createdBy,
            )
        return if (result.isSuccess) {
            logger.info("Event created successfully!")
            HttpResponse.created(result.getOrNull())
        } else {
            logger.error("Error creating event: ${result.exceptionOrNull()?.message}")
            HttpResponse.serverError("Error creating event.")
        }
    }

    override fun purchaseTicket(
        eventId: UUID,
        @QueryValue(value = "username") username: String,
        @QueryValue(value = "quantity") quantity: Int,
    ): HttpResponse<*> {
        logger.info("Received request to purchase ticket for event: $eventId by user: $username")
        try {
            val result = ticketService.purchaseTicket(eventId, username, quantity)
            if (result.isSuccess)
                {
                    logger.info("Ticket purchased successfully!")
                    return HttpResponse.ok(result.getOrNull())
                } else {
                logger.error("Error purchasing ticket: ${result.exceptionOrNull()?.message}")
                return HttpResponse.serverError("Error purchasing ticket.")
            }
        } catch (ticketSoldOutException: TicketSoldOutException) {
            return HttpResponse.badRequest(ticketSoldOutException.message)
        } catch (eventNotFoundException: EventNotFoundException) {
            return HttpResponse.notFound(eventNotFoundException.message)
        } catch (insufficientWalletBalance: InsufficientWalletBalance) {
            return HttpResponse.badRequest(insufficientWalletBalance.message)
        } catch (userNotFoundException: UserNotFoundException) {
            return HttpResponse.notFound(userNotFoundException.message)
        }
    }

    override fun getTicketsByEventId(eventId: UUID): HttpResponse<List<Ticket>> {
        logger.info("Received request to get tickets for event: $eventId")
        val tickets = ticketService.getTicketsByEventId(eventId)
        return HttpResponse.ok(tickets)
    }

    override fun getEventsStats(eventId: UUID): HttpResponse<EventsStats> {
        logger.info("Received request to get stats for event: $eventId")
        val eventStats = eventsService.getEventsStats(eventId)
        return HttpResponse.ok(eventStats)
    }
}
