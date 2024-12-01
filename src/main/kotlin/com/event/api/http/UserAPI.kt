package com.event.api.http

import com.event.application.domain.User
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import java.math.BigDecimal

interface UserAPI {
    // Create a new user
    @Operation(
        summary = "Create a new user",
        description = "Create a new user",
        tags = ["users"],
    )
    @ApiResponse(responseCode = "201", description = "User created")
    @Post(value = "/users/create", produces = ["application/json"])
    fun createUser(
        @QueryValue(value = "name") name: String,
        @QueryValue(value = "email") email: String,
        @QueryValue(value = "username") username: String,
        @QueryValue(value = "wallet") wallet: BigDecimal,
    ): HttpResponse<User>
}
