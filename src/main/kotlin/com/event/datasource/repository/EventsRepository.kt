package com.event.datasource.repository

import com.event.datasource.entity.EventsEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDate
import java.util.UUID

@Repository
interface EventsRepository : CrudRepository<EventsEntity, UUID> {


    fun findByTitleContainingIgnoreCase(
        title: String,
        pageable: Pageable
    ): Page<EventsEntity>

    fun existsByTitleAndEventDate(
        title: String,
        eventDate: LocalDate
    ): Boolean
}