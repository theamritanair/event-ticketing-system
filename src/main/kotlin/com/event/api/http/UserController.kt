package com.event.api.http

import com.event.api.service.UserService
import com.event.application.domain.User
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

@Singleton
@Controller
class UserController(
    private val userService: UserService,
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java),
) : UserAPI {
    override fun createUser(
        name: String,
        email: String,
        username: String,
        wallet: BigDecimal,
    ): HttpResponse<User> {
        logger.info("Received request to create user with username: $username")
        val user = userService.createUser(name, email, username, wallet)
        return if (user.isSuccess)
            {
                HttpResponse.created(user.getOrNull())
            } else {
            HttpResponse.badRequest()
        }
    }
}
