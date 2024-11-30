package com.event.application.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
data class User(
    val id: String,
    val username: String,
    val name: String,
    val email: String,
    val createdAt: String,
    val walletBalance: String
)