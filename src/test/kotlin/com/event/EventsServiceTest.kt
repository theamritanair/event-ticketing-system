package com.event

import com.event.api.service.EventsService
import com.event.application.domain.EventStatus
import com.event.datasource.entity.EventsEntity
import com.event.datasource.repository.EventsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class EventsServiceTest : FunSpec({

    val eventsRepository = mockk<EventsRepository>()
    val eventsService = EventsService(eventsRepository)

    test("getAllEvents should return a list of events") {
        val event =
            EventsEntity(
                id = UUID.randomUUID(),
                title = "Sample Event",
                description = "Sample Description",
                eventDate = LocalDate.now(),
                totalTickets = 100,
                availableTickets = 100,
                ticketPrice = BigDecimal.TEN,
                createdBy = "admin",
                status = EventStatus.PUBLISHED.name,
            )
        every { eventsRepository.findAll() } returns listOf(event)

        val result = eventsService.getAllEvents()

        result.size shouldBe 1
        result[0].title shouldBe "Sample Event"
        verify { eventsRepository.findAll() }
    }
})
