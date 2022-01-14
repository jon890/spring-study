package com.bifos.springsecurity.repository

import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CalendarUserRepository : JpaRepository<CalendarUser, Int> {

    fun findByEmail(email: String): Optional<CalendarUser>

    fun findByEmailStartsWith(partialEmail: String): List<CalendarUser>
}