package com.event.datasource.repository

import com.event.application.domain.Events
import com.event.datasource.entity.EventsEntity
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface EventsRepository : CrudRepository<EventsEntity, UUID> {


    fun findByTitleContainingIgnoreCase(
        title: String,
        pageable: Pageable
    ): Page<EventsEntity>

    @Query("INSERT INTO events (id, name, description, location, date, time) VALUES (:id, :name, :description, :location, :date, :time)")
    fun createEvent(id: UUID, name: String, description: String, location: String, date: String, time: String): EventsEntity

    @Query("DELETE FROM events WHERE id = :id")
    fun deleteEvent(id: UUID): EventsEntity

    @Query("UPDATE events SET name = :name, description = :description, location = :location, date = :date, time = :time WHERE id = :id")
    fun updateEvent(id: UUID, name: String, description: String, location: String, date: String, time: String): EventsEntity

}