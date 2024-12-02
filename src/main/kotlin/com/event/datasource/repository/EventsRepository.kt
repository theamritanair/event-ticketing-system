package com.event.datasource.repository

import com.event.datasource.entity.EventsEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.time.LocalDate
import java.util.UUID

@Repository
interface EventsRepository : JpaRepository<EventsEntity, UUID> {
    fun findByTitleContainingIgnoreCase(
        title: String,
        pageable: Pageable,
    ): Page<EventsEntity>

    fun existsByTitleAndEventDate(
        title: String,
        eventDate: LocalDate,
    ): Boolean

    fun existsByIdAndEventDate(
        id: UUID,
        eventDate: LocalDate,
    ): Boolean

    fun findByEventDate(eventDate: LocalDate): EventsEntity?
}
