package com.event.utility

import com.event.application.constants.Constants.INVALID_DATE_FORMAT_ERROR
import com.event.application.constants.Constants.INVALID_TICKETS_ERROR
import com.event.application.constants.Constants.INVALID_TICKET_PRICE_ERROR
import java.math.BigDecimal
import java.time.LocalDate

object Helper {
    fun validateCreateEventRequest(
        eventDate: String,
        totalTickets: Int,
        availableTickets: Int,
        ticketPrice: BigDecimal,
    ): String? {
        if (availableTickets > totalTickets) {
            return INVALID_TICKETS_ERROR
        }
        if (!validateEventDate(eventDate)) {
            return INVALID_DATE_FORMAT_ERROR
        }
        if (ticketPrice <= BigDecimal.ZERO) {
            return INVALID_TICKET_PRICE_ERROR
        }
        return null
    }

    fun validatePurchaseTicketRequest(
        quantity: Int,
        eventDate: String,
    ): String? {
        if (quantity <= 0 || quantity >= 5) {
            return INVALID_TICKETS_ERROR
        }
        if (!validateEventDate(eventDate)) {
            return INVALID_DATE_FORMAT_ERROR
        }
        return null
    }

    private fun validateEventDate(date: String): Boolean {
        return try {
            LocalDate.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}
