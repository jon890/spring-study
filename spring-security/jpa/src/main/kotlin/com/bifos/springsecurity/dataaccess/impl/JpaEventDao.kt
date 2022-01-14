package com.bifos.springsecurity.dataaccess.impl

import com.bifos.springsecurity.dataaccess.EventDao
import com.bifos.springsecurity.domain.Event
import com.bifos.springsecurity.repository.EventRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class JpaEventDao(
    private val eventRepository: EventRepository,
) : EventDao {

    override fun getEvent(eventId: Int): Event {
        return eventRepository.findById(eventId)
            .orElseThrow { EntityNotFoundException("Event Not Found!! id : $eventId") }
    }

    override fun createEvent(event: Event): Int {
        val newEvent = eventRepository.save(event)
        return newEvent.id!!
    }

    override fun findForUser(userId: Int): List<Event> {
        return eventRepository.findByOwner(userId)
    }

    override fun getEvents(): List<Event> {
        return eventRepository.findAll()
    }
}