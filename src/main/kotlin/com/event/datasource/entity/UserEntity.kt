package com.event.datasource.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "users")
class UserEntity (

    @Id
    @Column(name = "id", nullable = false)
    var id: String? = null,

    @Column(name = "username", nullable = false)
    var username: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name="created_at", nullable = false)
    var createdAt: Date,

    @Column(name="wallet", nullable = false)
    var walletBalance: BigDecimal,
)