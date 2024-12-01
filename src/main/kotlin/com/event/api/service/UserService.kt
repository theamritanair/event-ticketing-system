package com.event.api.service

import com.event.datasource.repository.UserRepository
import jakarta.inject.Singleton

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
}
