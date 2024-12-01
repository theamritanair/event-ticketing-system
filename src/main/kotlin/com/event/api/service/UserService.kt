package com.event.api.service

import com.event.application.domain.User
import com.event.datasource.mapper.toUser
import com.event.datasource.mapper.toUserEntity
import com.event.datasource.repository.UserRepository
import jakarta.inject.Singleton
import java.math.BigDecimal

@Singleton
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUserExists(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun isUserAdmin(username: String): Boolean {
        return userRepository.findByUsernameIgnoreCase(username)?.role == "ADMIN"
    }

    fun createUser(
        name: String,
        email: String,
        username: String,
        wallet: BigDecimal,
    ): Result<User> {
        val user =
            User(
                name = name,
                email = email,
                username = username,
                walletBalance = wallet,
            )
        val newUser = userRepository.save(user.toUserEntity())
        return Result.success(newUser.toUser())
    }
}
