package com.event.datasource.repository

import com.event.datasource.entity.UserEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun existsByUsername(username: String): Boolean

    fun findByUsernameIgnoreCase(username: String): UserEntity?
}
