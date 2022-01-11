package com.bifos.springsecurity.service.impl

import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.service.CalendarService
import com.bifos.springsecurity.service.UserContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class SpringSecurityUserContext(
    private val calendarService: CalendarService,
    private val userDetailsService: UserDetailsService
) : UserContext {

    override fun getCurrentUser(): CalendarUser? {
        val context = SecurityContextHolder.getContext()
        val authentication = context.authentication ?: return null

        val email = authentication.name
        return calendarService.findUserByEmail(email)
    }

    override fun setCurrentUser(user: CalendarUser) {
        throw UnsupportedOperationException()
    }
}