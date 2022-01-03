package com.bifos.springsecurity

import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.domain.Event
import java.util.*


/**
 * CalendarStubs.admin1()
 * CalendarStubs.user1()
 * CalendarStubs.admin1()
 */
class CalendarStubs {

    //-----------------------------------------------------------------------//
    // CalendarUser
    //-----------------------------------------------------------------------//
    fun user1(): CalendarUser {
        return CalendarUser(
            email = "user1@example.com",
            firstName = "User",
            lastName = "1",
        ).apply { id = 0 }
    }

    fun admin1(): CalendarUser {
        return CalendarUser(
            email = "admin1@example.com",
            firstName = "Admin",
            lastName = "1",
        ).apply { id = 1 }
    }

    fun user2(): CalendarUser {
        return CalendarUser(
            email = "user2@example.com",
            firstName = "User",
            lastName = "2",
        ).apply { id = 2 }
    }

    fun bob1(): CalendarUser? {
        return CalendarUser(
            email = "bob1@example.com",
            firstName = "Bob",
            lastName = "One",
        ).apply { id = 0 }
    }

    //-----------------------------------------------------------------------//
    // Events
    //-----------------------------------------------------------------------//
    fun toCreate(): Event {
        return Event(
            `when` = Calendar.getInstance(),
            summary = "This is a test..",
            description = "of the emergency broadcast system",
            owner = admin1(),
            attendee = user1()
        )
    }
}