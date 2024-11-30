package com.event.datasource.entity

import io.micronaut.data.annotation.DateCreated
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
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

    @DateCreated
    @Column(name="purchase_date_time")
    var purchaseDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name="quantity")
    var quantity: Int,

    @Column(name="total_price")
    var totalPrice: BigDecimal
)