package com.event.datasource.entity

import io.micronaut.data.annotation.DateCreated
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "id", nullable = false)
    var id: String? = null,
    @Column(name = "username", nullable = false)
    var username: String,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "email", nullable = false)
    var email: String,
    @DateCreated
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDate,
    @Column(name = "wallet", nullable = false)
    var walletBalance: BigDecimal,
)
