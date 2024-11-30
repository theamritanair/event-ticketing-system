package com.event.api

import com.event.application.domain.Events
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import java.math.BigDecimal
import java.util.*

interface EventsAPI {

    @Operation(
        summary = "Get all events",
        operationId = "getAllEvents",
        tags = ["events"]
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @Get(value = "/events/", produces = ["application/json"])
    fun getAllEvents(): HttpResponse<List<Events>>

    @Operation(
        summary = "Get event by id",
        tags = ["events"]
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @Get(value = "/events/{id}", produces = ["application/json"])
    fun getEventById(id: UUID): HttpResponse<Events>

    @Operation(
        summary = "Get event by name",
        description = "Get event by their name. Returns a list of events which have the same name or similar name",
        tags = ["events"]
    )
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @Get(value = "/events/search", produces = ["application/json"])
    fun getEventsByName(@QueryValue("name") name: String,
                        @QueryValue("page") page: Int = 0,): HttpResponse<List<Events>>

    @Operation(
        summary = "Create an event",
        description = "Create an event",
        tags = ["events"]
    )
    @ApiResponse(responseCode = "201", description = "Event created")
    @Post(value = "/events/create", produces = ["application/json"])
    fun createEvent(
        @QueryValue("name") name: String,
        @QueryValue("description") description: String,
        @QueryValue("date") date: String,
        @QueryValue("total_tickets") totalTickets: Int,
        @QueryValue("available_tickets") availableTickets: Int,
        @QueryValue("ticket_price") ticketPrice: BigDecimal,
        @QueryValue("created_by") createdBy: String
    ): HttpResponse<*>


}