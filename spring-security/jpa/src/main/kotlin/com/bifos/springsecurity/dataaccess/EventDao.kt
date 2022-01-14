package com.bifos.springsecurity.dataaccess

import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.domain.Event


/**
 * An interface for managing {@link Event}'s.
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 */
interface EventDao {

    /**
     * Given an id gets an {@link Event}.
     *
     * @param eventId the [Event.id]
     * @return the [Event]. Cannot be null.
     * @throws RuntimeException
     *             if the {@link Event} cannot be found.
     */
    fun getEvent(eventId: Int): Event

    /**
     * Creates a [Event] and returns the new id for that [Event].
     *
     * @param event
     * the [Event] to create. Note that the [Event.id] should be null.
     * @return the new id for the [Event]
     * @throws RuntimeException
     * if [Event.id] is non-null.
     */
    fun createEvent(event: Event): Int

    /**
     * Finds the [Event]'s that are intended for the [CalendarUser].
     *
     * @param userId
     * the [CalendarUser.id] to obtain [Event]'s for.
     * @return a non-null [List] of [Event]'s intended for the specified [CalendarUser]. If the
     * [CalendarUser] does not exist an empty List will be returned.
     */
    fun findForUser(userId: Int): List<Event>

    /**
     * Gets all the available [Event]'s.
     *
     * @return a non-null [List] of [Event]'s
     */
    fun getEvents(): List<Event>
}