package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal

@Serdeable.Serializable
data class User(
    val username: String,
    val name: String,
    val email: String,
    val walletBalance: BigDecimal,
    val role: String = "USER",
)
