package com.event.datasource.repository

import com.event.datasource.entity.TicketEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface TicketRepository : CrudRepository<TicketEntity, UUID>