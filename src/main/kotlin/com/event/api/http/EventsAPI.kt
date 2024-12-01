package com.event.api.http

import com.event.application.domain.Events
import com.event.application.domain.UpdateEventRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import java.math.BigDecimal
import java.util.UUID

interface EventsAPI {
    @Operation(
        summary = "Get all events",
        operationId = "getAllEvents",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @Get(value = "/events/", produces = ["application/json"])
    fun getAllEvents(): HttpResponse<List<Events>>

    @Operation(
        summary = "Get event by id",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @Get(value = "/events/{id}", produces = ["application/json"])
    fun getEventById(id: UUID): HttpResponse<Events>

    @Operation(
        summary = "Get event by name",
        description = "Get event by their name. Returns a list of events which have the same name or similar name",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @Get(value = "/events/search", produces = ["application/json"])
    fun getEventsByName(
        @QueryValue("name") name: String,
        @QueryValue("page") page: Int = 0,
    ): HttpResponse<List<Events>>

    @Operation(
        summary = "Create an event",
        description = "Create an event",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "201", description = "Event created")
    @Post(value = "/events/create", produces = ["application/json"])
    fun createEvent(
        @QueryValue("name") name: String,
        @QueryValue("description") description: String,
        @QueryValue("event_date") eventDate: String,
        @QueryValue("total_tickets") totalTickets: Int,
        @QueryValue("available_tickets") availableTickets: Int,
        @QueryValue("ticket_price") ticketPrice: BigDecimal,
        @QueryValue("created_by") createdBy: String,
    ): HttpResponse<*>

    @Operation(
        summary = "Update an event",
        description = "Update an event",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Event updated")
    @Post(value = "/events/{eventId}/update", produces = ["application/json"])
    fun updateEvent(
        eventId: UUID,
        @Body updateEventRequest: UpdateEventRequest,
    ): HttpResponse<*>

    @Operation(
        summary = "Delete an event",
        description = "Delete an event",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Event deleted")
    @Post(value = "/events/{eventId}/delete", produces = ["application/json"])
    fun deleteEvent(eventId: UUID): HttpResponse<*>

    @Operation(
        summary = "Purchase a ticket",
        description = "Purchase a ticket for an event by providing the event id, username and quantity",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "201", description = "Event created")
    @Post(value = "/events/{eventId}/purchase", produces = ["application/json"])
    fun purchaseTicket(
        eventId: UUID,
        @QueryValue("username") username: String,
        @QueryValue("quantity") quantity: Int,
        @QueryValue("event_date") eventDate: String,
    ): HttpResponse<*>

    @Operation(
        summary = "Get all tickets for an event",
        description = "Get all tickets for an event",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @Get(value = "/events/{eventId}/tickets", produces = ["application/json"])
    fun getTicketsByEventId(
        eventId: UUID,
        @QueryValue("username") username: String,
    ): HttpResponse<*>

    @Operation(
        summary = "Get event stats",
        description = "Get event stats",
        tags = ["events"],
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @Get(value = "/events/{eventId}/stats", produces = ["application/json"])
    fun getEventsStats(eventId: UUID): HttpResponse<*>
}
