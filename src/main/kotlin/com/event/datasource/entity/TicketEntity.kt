package com.event.datasource.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "tickets")
class TicketEntity(

    @Id
    @Column(name = "transaction_id", nullable = false)
    var transactionId: UUID,

    @Column(name= "event_id")
    var eventId: UUID,

    @Column(name = "user_id")
    var userId: String,

    @Column(name="purchase_date")
    var purchaseDate: LocalDate,
)