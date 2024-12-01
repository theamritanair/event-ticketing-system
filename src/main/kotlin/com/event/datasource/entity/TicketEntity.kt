package com.event.datasource.entity

import io.micronaut.data.annotation.DateCreated
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tickets")
class TicketEntity(
    @Id
    @Column(name = "transaction_id", nullable = false)
    var transactionId: UUID,
    @Column(name = "event_id")
    var eventId: UUID,
    @Column(name = "user_id")
    var userId: Long,
    @DateCreated
    @Column(name = "purchase_date_time")
    var purchaseDateTime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "quantity")
    var quantity: Int,
    @Column(name = "total_price")
    var totalPrice: BigDecimal,
)
