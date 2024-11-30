package com.event.application.exception

class EventNotFoundException(s: String) : Exception(s)

class TicketSoldOutException(s: String) : Exception(s)

class UserNotFoundException(s: String) : Exception(s)

class InsufficientWalletBalance(s: String) : Exception(s)
