package com.event.application.constants

object Constants {

    val ADMIN_USERS = listOf("admin", "AD001", "AD002", "AD003")
    const val UNAUTHORIZED_USER_ERROR = "Error: User is not authorized to create events."
    const val INVALID_TICKETS_ERROR = "Error: Available tickets cannot exceed total tickets."
    const val INVALID_DATE_FORMAT_ERROR = "Error: Invalid date format. Please use yyyy-MM-dd"
    const val INVALID_TICKET_PRICE_ERROR = "Error: Ticket price should be greater than zero."
}