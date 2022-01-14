package com.bifos.springsecurity.repository

import com.bifos.springsecurity.domain.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Int> {

    fun findByOwner(userId: Int): List<Event>
}