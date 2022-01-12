package com.bifos.springsecurity.service.impl

import com.bifos.springsecurity.dataaccess.CalendarUserDao
import com.bifos.springsecurity.dataaccess.EventDao
import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.domain.Event
import com.bifos.springsecurity.service.CalendarService
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service

/**
 * A default implementation of [com.bifos.springsecurity.service.CalendarService]
 * that delegates to [com.bifos.springsecurity.dataaccess.EventDao] and [com.bifos.springsecurity.dataaccess.CalendarUserDao].
 *
 * @author Rob Winch
 * @author Mick Knuston
 * @author BiFoS (jon89071@gmail.com)
 */
@Service
class DefaultCalendarService(
    val eventDao: EventDao,
    val userDao: CalendarUserDao,
) : CalendarService {

    override fun getUser(id: Int): CalendarUser {
        return userDao.getUser(id)
    }

    override fun findUserByEmail(email: String): CalendarUser? {
        return userDao.findUserByEmail(email)
    }

    override fun findUsersByEmail(partialEmail: String): List<CalendarUser> {
        return userDao.findUsersByEmail(partialEmail)
    }

    override fun createUser(user: CalendarUser): Int {
        return userDao.createUser(user)
    }

    override fun getEvent(eventId: Int): Event {
        return eventDao.getEvent(eventId)
    }

    override fun createEvent(event: Event): Int {
        return eventDao.createEvent(event)
    }

    override fun findForUser(userId: Int): List<Event> {
        return eventDao.findForUser(userId)
    }

    override fun getEvents(): List<Event> {
        return eventDao.getEvents()
    }
}